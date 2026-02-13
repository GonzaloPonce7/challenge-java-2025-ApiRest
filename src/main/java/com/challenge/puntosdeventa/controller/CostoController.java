package com.challenge.puntosdeventa.controller;

import com.challenge.puntosdeventa.DTO.request.CostoRequest;
import com.challenge.puntosdeventa.DTO.response.CaminoMinimoResponse;
import com.challenge.puntosdeventa.DTO.response.CostoResponse;
import com.challenge.puntosdeventa.service.impl.CostoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costos")
@RequiredArgsConstructor
public class CostoController {

    private final CostoServiceImpl service;

    @PostMapping
    public ResponseEntity<Void> agregarCosto(@Valid @RequestBody CostoRequest request) {
        service.agregarCosto(request.idA(), request.idB(), request.costo());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removerCosto(@RequestParam Long puntoA, @RequestParam Long puntoB) {
        service.removerCosto(puntoA, puntoB);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/conexiones")
    public ResponseEntity<List<CostoResponse>> obtenerConexiones(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerCostosDirectos(id));
    }

    @GetMapping("/camino-minimo")
    public ResponseEntity<CaminoMinimoResponse> calcularCaminoMinimo(
            @RequestParam Long origen,
            @RequestParam Long destino) {
        return ResponseEntity.ok(service.calcularCaminoMinimo(origen, destino));
    }
}
