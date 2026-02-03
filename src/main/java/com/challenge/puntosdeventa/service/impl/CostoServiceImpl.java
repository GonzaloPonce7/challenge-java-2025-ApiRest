package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.response.CaminoMinimoResponse;
import com.challenge.puntosdeventa.DTO.response.ConexionResponse;
import com.challenge.puntosdeventa.exception.CaminoNoEncontradoException;
import com.challenge.puntosdeventa.exception.PuntoVentaNotFoundException;
import com.challenge.puntosdeventa.entity.PuntoVenta;
import com.challenge.puntosdeventa.service.ICostoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CostoServiceImpl implements ICostoService {

    private final RedisTemplate<String, Double> redisTemplate;
    private final PuntoVentaServiceImpl puntoVentaService;

    private static final String KEY_PREFIX = "costo:";

    /**
     * Genera la key de Redis para un costo
     * Siempre usa el ID menor primero para consistencia
     */
    private String generarKey(Long puntoA, Long puntoB) {
        Long min = Math.min(puntoA, puntoB);
        Long max = Math.max(puntoA, puntoB);
        return KEY_PREFIX + min + ":" + max;
    }

    /**
     * Agrega un costo bidireccional entre dos puntos de venta
     */
    public void agregarCosto(Long puntoA, Long puntoB, Double costo) {
        // Validar que ambos puntos existan
        if (puntoVentaService.existPunto(puntoA)) {
            throw new PuntoVentaNotFoundException(puntoA);
        }
        if (puntoVentaService.existPunto(puntoB)) {
            throw new PuntoVentaNotFoundException(puntoB);
        }

        // Validar que no sean el mismo punto
        if (puntoA.equals(puntoB)) {
            throw new IllegalArgumentException("No se puede create un costo de un punto a sí mismo");
        }

        // Validar que el costo sea positivo
        if (costo < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo");
        }

        // Guardar en Redis (solo una vez, usando min:max)
        String key = generarKey(puntoA, puntoB);
        redisTemplate.opsForValue().set(key, costo);
    }

    /**
     * Remueve un costo bidireccional entre dos puntos
     */
    public void removerCosto(Long puntoA, Long puntoB) {
        String key = generarKey(puntoA, puntoB);
        redisTemplate.delete(key);
    }

    /**
     * Obtiene todas las conexiones directas de un punto de venta
     */
    public List<ConexionResponse> obtenerConexiones(Long puntoVentaId) {
        // Validar que el punto exista
        if (puntoVentaService.existPunto(puntoVentaId)) {
            throw new PuntoVentaNotFoundException(puntoVentaId);
        }

        List<ConexionResponse> conexiones = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");

        if (keys != null) {
            for (String key : keys) {
                // Extraer IDs de la key (formato: "costo:1:2")
                String[] parts = key.replace(KEY_PREFIX, "").split(":");
                Long id1 = Long.parseLong(parts[0]);
                Long id2 = Long.parseLong(parts[1]);

                // Si el punto está en esta conexión
                if (id1.equals(puntoVentaId) || id2.equals(puntoVentaId)) {
                    Long puntoConectadoId = id1.equals(puntoVentaId) ? id2 : id1;
                    Double costo = redisTemplate.opsForValue().get(key);
                    PuntoVenta puntoConectado = puntoVentaService.getById(puntoConectadoId);

                    conexiones.add(new ConexionResponse(
                            puntoConectadoId,
                            puntoConectado.nombre(),
                            costo
                    ));
                }
            }
        }

        return conexiones;
    }

    /**
     * Calcula el camino de costo mínimo usando el algoritmo de Dijkstra
     */
    public CaminoMinimoResponse calcularCaminoMinimo(Long origen, Long destino) {
        // Validar que ambos puntos existan
        if (puntoVentaService.existPunto(origen)) {
            throw new PuntoVentaNotFoundException(origen);
        }
        if (puntoVentaService.existPunto(destino)) {
            throw new PuntoVentaNotFoundException(destino);
        }

        // Si origen y destino son iguales
        if (origen.equals(destino)) {
            PuntoVenta punto = puntoVentaService.getById(origen);
            return new CaminoMinimoResponse(0.0, List.of(punto.nombre()));
        }

        // Construir grafo en memoria desde Redis
        Map<Long, Map<Long, Double>> grafo = construirGrafo();

        // Ejecutar Dijkstra
        Map<Long, Double> distancias = new HashMap<>();
        Map<Long, Long> predecesores = new HashMap<>();
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distancia));
        Set<Long> visitados = new HashSet<>();

        // Inicializar distancias
        distancias.put(origen, 0.0);
        cola.offer(new NodoDistancia(origen, 0.0));

        while (!cola.isEmpty()) {
            NodoDistancia actual = cola.poll();
            Long nodoActual = actual.nodo;

            if (visitados.contains(nodoActual)) {
                continue;
            }

            visitados.add(nodoActual);

            // Si llegamos al destino, terminamos
            if (nodoActual.equals(destino)) {
                break;
            }

            // Explorar vecinos
            Map<Long, Double> vecinos = grafo.get(nodoActual);
            if (vecinos != null) {
                for (Map.Entry<Long, Double> entry : vecinos.entrySet()) {
                    Long vecino = entry.getKey();
                    Double costoArista = entry.getValue();

                    if (!visitados.contains(vecino)) {
                        Double nuevaDistancia = distancias.get(nodoActual) + costoArista;
                        Double distanciaActual = distancias.getOrDefault(vecino, Double.MAX_VALUE);

                        if (nuevaDistancia < distanciaActual) {
                            distancias.put(vecino, nuevaDistancia);
                            predecesores.put(vecino, nodoActual);
                            cola.offer(new NodoDistancia(vecino, nuevaDistancia));
                        }
                    }
                }
            }
        }

        // Verificar si se encontró un camino
        if (!distancias.containsKey(destino)) {
            throw new CaminoNoEncontradoException(origen, destino);
        }

        // Reconstruir el camino
        List<String> camino = reconstruirCamino(predecesores, origen, destino);
        Double costoTotal = distancias.get(destino);

        return new CaminoMinimoResponse(costoTotal, camino);
    }

    /**
     * Construye el grafo en memoria desde Redis
     */
    private Map<Long, Map<Long, Double>> construirGrafo() {
        Map<Long, Map<Long, Double>> grafo = new HashMap<>();
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");

        if (keys != null) {
            for (String key : keys) {
                // Extraer IDs de la key (formato: "costo:1:2")
                String[] parts = key.replace(KEY_PREFIX, "").split(":");
                Long id1 = Long.parseLong(parts[0]);
                Long id2 = Long.parseLong(parts[1]);
                Double costo = redisTemplate.opsForValue().get(key);

                if (costo != null) {
                    // Agregar bidireccional
                    grafo.computeIfAbsent(id1, k -> new HashMap<>()).put(id2, costo);
                    grafo.computeIfAbsent(id2, k -> new HashMap<>()).put(id1, costo);
                }
            }
        }

        return grafo;
    }

    /**
     * Reconstruye el camino desde origen a destino usando los predecesores
     */
    private List<String> reconstruirCamino(Map<Long, Long> predecesores, Long origen, Long destino) {
        List<Long> caminoIds = new ArrayList<>();
        Long actual = destino;

        while (actual != null) {
            caminoIds.add(0, actual); // Agregar al inicio
            actual = predecesores.get(actual);
        }

        // Convertir IDs a nombres
        List<String> caminoNombres = new ArrayList<>();
        for (Long id : caminoIds) {
            PuntoVenta punto = puntoVentaService.getById(id);
            caminoNombres.add(punto.nombre());
        }

        return caminoNombres;
    }

    /**
     * Inicializa los datos de costos en Redis
     */
    public void inicializarDatos() {
        // Solo inicializar si no hay datos
        Set<String> keys = redisTemplate.keys(KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            agregarCosto(1L, 2L, 2.0);
            agregarCosto(1L, 3L, 3.0);
            agregarCosto(2L, 3L, 5.0);
            agregarCosto(2L, 4L, 10.0);
            agregarCosto(1L, 4L, 11.0);
            agregarCosto(4L, 5L, 5.0);
            agregarCosto(2L, 5L, 14.0);
            agregarCosto(6L, 7L, 32.0);
            agregarCosto(8L, 9L, 11.0);
            agregarCosto(10L, 7L, 5.0);
            agregarCosto(3L, 8L, 10.0);
            agregarCosto(5L, 8L, 30.0);
            agregarCosto(10L, 5L, 5.0);
            agregarCosto(4L, 6L, 6.0);
        }
    }

    /**
     * Clase auxiliar para el algoritmo de Dijkstra
     */
    private static class NodoDistancia {
        Long nodo;
        Double distancia;

        NodoDistancia(Long nodo, Double distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }
    }
}
