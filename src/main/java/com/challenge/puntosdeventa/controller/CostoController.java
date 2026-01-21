package com.challenge.puntosdeventa.controller;

import com.challenge.puntosdeventa.DTO.request.CostoRequest;
import com.challenge.puntosdeventa.DTO.response.CaminoMinimoResponse;
import com.challenge.puntosdeventa.DTO.response.ConexionResponse;
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

    /**
     * Agrega un costo entre dos puntos de venta
     * POST /api/costos
     */
    @PostMapping
    public ResponseEntity<String> agregarCosto(@Valid @RequestBody CostoRequest request) {
        service.agregarCosto(
                request.puntoVentaA(),
                request.puntoVentaB(),
                request.costo()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Costo agregado exitosamente entre puntos " +
                        request.puntoVentaA() + " y " + request.puntoVentaB());
    }

    /**
     * Remueve un costo entre dos puntos de venta
     * DELETE /api/costos?puntoA={a}&puntoB={b}
     */
    @DeleteMapping
    public ResponseEntity<String> removerCosto(
            @RequestParam Long puntoA,
            @RequestParam Long puntoB) {
        service.removerCosto(puntoA, puntoB);
        return ResponseEntity.ok("Costo removido exitosamente entre puntos " +
                puntoA + " y " + puntoB);
    }

    /**
     * Obtiene todas las conexiones directas de un punto de venta
     * GET /api/costos/{id}/conexiones
     */
    @GetMapping("/{id}/conexiones")
    public ResponseEntity<List<ConexionResponse>> obtenerConexiones(@PathVariable Long id) {
        List<ConexionResponse> conexiones = service.obtenerConexiones(id);
        return ResponseEntity.ok(conexiones);
    }

    /**
     * Calcula el camino de costo mínimo entre dos puntos
     * GET /api/costos/camino-minimo?origen={a}&destino={b}
     */
    @GetMapping("/camino-minimo")
    public ResponseEntity<CaminoMinimoResponse> calcularCaminoMinimo(
            @RequestParam Long origen,
            @RequestParam Long destino) {
        CaminoMinimoResponse camino = service.calcularCaminoMinimo(origen, destino);
        return ResponseEntity.ok(camino);
    }
}
