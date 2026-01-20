package com.challenge.puntosdeventa.DTO.response;

public record ConexionResponse(

        Long puntoVentaId,
        String nombre,
        Double costo
) {}
