package com.challenge.puntosdeventa.controller;

import com.challenge.puntosdeventa.DTO.request.PuntoVentaRequest;
import com.challenge.puntosdeventa.entity.PuntoVenta;
import com.challenge.puntosdeventa.service.impl.PuntoVentaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puntos-venta")
@RequiredArgsConstructor
public class PuntoVentaController {

    private final PuntoVentaServiceImpl service;

    /**
     * Obtiene todos los puntos de venta
     * GET /api/puntos-venta
     */
    @GetMapping
    public ResponseEntity<List<PuntoVenta>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtiene un punto de venta por ID
     * GET /api/puntos-venta/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PuntoVenta> obtenerPorId(Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    /**
     * Crea un nuevo punto de venta
     * POST /api/puntos-venta
     */
    @PostMapping("/")
    public ResponseEntity<PuntoVenta> crear(@Valid @RequestBody PuntoVentaRequest puntoVenta) {
        return ResponseEntity.ok(service.crear(String.valueOf(puntoVenta)));
    }

    /**
     * Actualiza un punto de venta existente
     * PUT /api/puntos-venta/{id}
     */
    @PutMapping
    public ResponseEntity<PuntoVenta> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PuntoVentaRequest request) {
        PuntoVenta puntoVentaActualizado = service.actualizar(id, request.nombre());
        return ResponseEntity.ok(puntoVentaActualizado);
    }

    /**
     * Elimina un punto de venta
     * DELETE /api/puntos-venta/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
