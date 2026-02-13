package com.challenge.puntosdeventa.controller;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.DTO.response.AcreditacionResponse;
import com.challenge.puntosdeventa.service.impl.AcreditacionServiceImpl;
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

    private final AcreditacionServiceImpl service;

    @PostMapping
    public ResponseEntity<AcreditacionResponse> crear(@Valid @RequestBody AcreditacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<AcreditacionResponse>> obtenerTodas() {
        return ResponseEntity.ok(service.getAll());
    }
}
