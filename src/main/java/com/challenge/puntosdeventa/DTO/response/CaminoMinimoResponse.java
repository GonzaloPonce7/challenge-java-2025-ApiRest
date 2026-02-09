package com.challenge.puntosdeventa.DTO.response;

import java.util.List;

public record CaminoMinimoResponse(
        Long origenId,
        String origenNombre,
        Long destinoId,
        String destinoNombre,
        Double costoMinimo,
        List<PuntoVentaResponse> camino
) {}
