package com.challenge.puntosdeventa.DTO.response;

import java.util.List;

public record CaminoMinimoResponse(

        Double costoTotal,
        List<String> camino
) {}
