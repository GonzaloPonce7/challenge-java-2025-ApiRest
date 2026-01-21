package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.entity.PuntoVenta;
import com.challenge.puntosdeventa.exception.PuntoVentaNotFoundException;
import com.challenge.puntosdeventa.exception.PuntoVentaDuplicadoException;
import com.challenge.puntosdeventa.service.IPuntoVentaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PuntoVentaServiceImpl implements IPuntoVentaService {

    // Caché en memoria thread-safe
    private final ConcurrentHashMap<Long, PuntoVenta> cache = new ConcurrentHashMap<>();

    // Generador de IDs thread-safe
    private final AtomicLong idGenerator = new AtomicLong(10);

    // Constructor: inicializa datos
    public PuntoVentaServiceImpl() {
        inicializarCache();
    }

    /**
     * Inicializa el caché con los 10 puntos de venta requeridos por la consigna
     */
    private void inicializarCache() {
        cache.put(1L, new PuntoVenta("1", "CABA"));
        cache.put(2L, new PuntoVenta("2", "GBA_1"));
        cache.put(3L, new PuntoVenta("3", "GBA_2"));
        cache.put(4L, new PuntoVenta("4", "Santa Fe"));
        cache.put(5L, new PuntoVenta("5", "Córdoba"));
        cache.put(6L, new PuntoVenta("6", "Misiones"));
        cache.put(7L, new PuntoVenta("7", "Salta"));
        cache.put(8L, new PuntoVenta("8", "Chubut"));
        cache.put(9L, new PuntoVenta("9", "Santa Cruz"));
        cache.put(10L, new PuntoVenta("10", "Catamarca"));
    }

    /**
     * Obtiene todos los puntos de venta
     */
    public List<PuntoVenta> obtenerTodos() {
        return new ArrayList<>(cache.values());
    }

    /**
     * Obtiene un punto de venta por ID
     */
    public PuntoVenta obtenerPorId(Long id) {
        PuntoVenta puntoVenta = cache.get(id);
        if (puntoVenta == null) {
            throw new PuntoVentaNotFoundException(id);
        }
        return puntoVenta;
    }

    /**
     * Verifica si existe un punto de venta
     */
    public boolean existe(Long id) {
        return cache.containsKey(id);
    }

    /**
     * Crea un nuevo punto de venta
     * Valida que no exista otro punto de venta con el mismo nombre
     */
    public PuntoVenta crear(String nombre) {
        // Validar duplicado por nombre
        boolean nombreExiste = cache.values().stream()
                .anyMatch(pv -> pv.nombre().equalsIgnoreCase(nombre.trim()));

        if (nombreExiste) {
            throw new PuntoVentaDuplicadoException(nombre);
        }

        Long nuevoId = idGenerator.incrementAndGet();
        PuntoVenta nuevoPuntoVenta = new PuntoVenta(String.valueOf(nuevoId), nombre.trim());
        cache.put(nuevoId, nuevoPuntoVenta);
        return nuevoPuntoVenta;
    }

    /**
     * Actualiza un punto de venta existente
     */
    public PuntoVenta actualizar(Long id, String nuevoNombre) {
        if (!cache.containsKey(id)) {
            throw new PuntoVentaNotFoundException(id);
        }
        PuntoVenta puntoVentaActualizado = new PuntoVenta(String.valueOf(id), nuevoNombre);
        cache.put(id, puntoVentaActualizado);
        return puntoVentaActualizado;
    }

    /**
     * Elimina un punto de venta
     */
    public void eliminar(Long id) {
        if (!cache.containsKey(id)) {
            throw new PuntoVentaNotFoundException(id);
        }
        cache.remove(id);
    }
}