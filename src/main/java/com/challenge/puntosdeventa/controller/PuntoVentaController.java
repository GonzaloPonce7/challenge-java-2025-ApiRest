package com.challenge.puntosdeventa.controller;

import com.challenge.puntosdeventa.DTO.request.PuntoVentaRequest;
import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
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
    public ResponseEntity<List<PuntoVentaResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    /**
     * Obtiene un punto de venta por ID
     * GET /api/puntos-venta/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PuntoVentaResponse> getById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Crea un nuevo punto de venta
     * POST /api/puntos-venta
     */
    @PostMapping("/")
    public ResponseEntity<PuntoVentaResponse> crear(@Valid @RequestBody PuntoVentaRequest puntoVenta) {
        return ResponseEntity.ok(service.create(String.valueOf(puntoVenta)));
    }

    /**
     * Actualiza un punto de venta existente
     * PUT /api/puntos-venta/{id}
     */
    @PutMapping
    public ResponseEntity<PuntoVentaResponse> actualizar(@PathVariable Long id, @Valid @RequestBody PuntoVentaRequest request) {
        PuntoVentaResponse puntoVentaActualizado = service.update(id, request.nombre());
        return ResponseEntity.ok(puntoVentaActualizado);
    }

    /**
     * Elimina un punto de venta
     * DELETE /api/puntos-venta/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
