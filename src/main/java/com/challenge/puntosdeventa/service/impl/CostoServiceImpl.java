package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.response.CaminoMinimoResponse;
import com.challenge.puntosdeventa.DTO.response.ConexionResponse;
import com.challenge.puntosdeventa.exception.CaminoNoEncontradoException;
import com.challenge.puntosdeventa.exception.PuntoVentaNotFoundException;
import com.challenge.puntosdeventa.entity.PuntoVenta;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CostoServiceImpl {

    private final PuntoVentaServiceImpl puntoVentaServiceImpl;

    // Grafo en memoria: Map<PuntoVentaId, Map<PuntoVentaConectado, Costo>>
    private final ConcurrentHashMap<Long, ConcurrentHashMap<Long, Double>> grafo = new ConcurrentHashMap<>();

    public CostoServiceImpl(PuntoVentaServiceImpl puntoVentaServiceImpl) {
        this.puntoVentaServiceImpl = puntoVentaServiceImpl;
        inicializarGrafo();
    }

    private void inicializarGrafo() {
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

    public void agregarCosto(Long puntoA, Long puntoB, Double costo) {
        if (!puntoVentaServiceImpl.existe(puntoA)) {
            throw new PuntoVentaNotFoundException(puntoA);
        }
        if (!puntoVentaServiceImpl.existe(puntoB)) {
            throw new PuntoVentaNotFoundException(puntoB);
        }
        if (puntoA.equals(puntoB)) {
            throw new IllegalArgumentException("No se puede crear un costo de un punto a sí mismo");
        }
        if (costo < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo");
        }

        grafo.computeIfAbsent(puntoA, k -> new ConcurrentHashMap<>()).put(puntoB, costo);
        grafo.computeIfAbsent(puntoB, k -> new ConcurrentHashMap<>()).put(puntoA, costo);
    }

    public void removerCosto(Long puntoA, Long puntoB) {
        if (grafo.containsKey(puntoA)) {
            grafo.get(puntoA).remove(puntoB);
        }
        if (grafo.containsKey(puntoB)) {
            grafo.get(puntoB).remove(puntoA);
        }
    }

    public List<ConexionResponse> obtenerConexiones(Long puntoVentaId) {
        if (!puntoVentaServiceImpl.existe(puntoVentaId)) {
            throw new PuntoVentaNotFoundException(puntoVentaId);
        }

        List<ConexionResponse> conexiones = new ArrayList<>();
        ConcurrentHashMap<Long, Double> conexionesDirectas = grafo.get(puntoVentaId);

        if (conexionesDirectas != null) {
            for (Map.Entry<Long, Double> entry : conexionesDirectas.entrySet()) {
                Long puntoConectadoId = entry.getKey();
                Double costo = entry.getValue();
                PuntoVenta puntoConectado = puntoVentaServiceImpl.obtenerPorId(puntoConectadoId);

                conexiones.add(new ConexionResponse(puntoConectadoId, puntoConectado.nombre(), costo));
            }
        }

        return conexiones;
    }

    public CaminoMinimoResponse calcularCaminoMinimo(Long origen, Long destino) {
        if (!puntoVentaServiceImpl.existe(origen)) {
            throw new PuntoVentaNotFoundException(origen);
        }
        if (!puntoVentaServiceImpl.existe(destino)) {
            throw new PuntoVentaNotFoundException(destino);
        }

        if (origen.equals(destino)) {
            PuntoVenta punto = puntoVentaServiceImpl.obtenerPorId(origen);
            return new CaminoMinimoResponse(0.0, List.of(punto.nombre()));
        }

        Map<Long, Double> distancias = new HashMap<>();
        Map<Long, Long> predecesores = new HashMap<>();
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distancia));
        Set<Long> visitados = new HashSet<>();

        distancias.put(origen, 0.0);
        cola.offer(new NodoDistancia(origen, 0.0));

        while (!cola.isEmpty()) {
            NodoDistancia actual = cola.poll();
            Long nodoActual = actual.nodo;

            if (visitados.contains(nodoActual)) {
                continue;
            }

            visitados.add(nodoActual);

            if (nodoActual.equals(destino)) {
                break;
            }

            ConcurrentHashMap<Long, Double> vecinos = grafo.get(nodoActual);
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

        if (!distancias.containsKey(destino)) {
            throw new CaminoNoEncontradoException(origen, destino);
        }

        List<String> camino = reconstruirCamino(predecesores, origen, destino);
        return new CaminoMinimoResponse(distancias.get(destino), camino);
    }

    private List<String> reconstruirCamino(Map<Long, Long> predecesores, Long origen, Long destino) {
        List<Long> caminoIds = new ArrayList<>();
        Long actual = destino;

        while (actual != null) {
            caminoIds.add(0, actual);
            actual = predecesores.get(actual);
        }

        List<String> caminoNombres = new ArrayList<>();
        for (Long id : caminoIds) {
            PuntoVenta punto = puntoVentaServiceImpl.obtenerPorId(id);
            caminoNombres.add(punto.nombre());
        }

        return caminoNombres;
    }

    private static class NodoDistancia {
        Long nodo;
        Double distancia;

        NodoDistancia(Long nodo, Double distancia) {
            this.nodo = nodo;
            this.distancia = distancia;
        }
    }
}
