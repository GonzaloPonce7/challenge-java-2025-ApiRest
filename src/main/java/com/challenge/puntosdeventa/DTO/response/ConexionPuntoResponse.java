package com.challenge.puntosdeventa.DTO.response;

import java.util.List;

public record ConexionPuntoResponse(
        Long idA,
        String nombreA,
        List<CaminoMinimoResponse> Conexiones
) {}
