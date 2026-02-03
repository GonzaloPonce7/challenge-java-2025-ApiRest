package com.challenge.puntosdeventa.controller;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import com.challenge.puntosdeventa.service.impl.AcreditacionesServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acreditaciones")
@RequiredArgsConstructor
public class AcreditacionesController {

    private final AcreditacionesServiceImpl service;

    /**
     * Crea una nueva acreditación (enriquecida y persistida)
     * POST /api/acreditaciones
     */
    @PostMapping
    public ResponseEntity<AcreditacionEntity> crear(@Valid @RequestBody AcreditacionRequest request) {
        AcreditacionEntity acreditacion = service.procesar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(acreditacion);
    }

    /**
     * Obtiene todas las acreditaciones
     * GET /api/acreditaciones
     */
    @GetMapping
    public ResponseEntity<List<AcreditacionEntity>> obtenerTodas() {
        List<AcreditacionEntity> acreditaciones = service.obtenerTodas();
        return ResponseEntity.ok(acreditaciones);
    }
}