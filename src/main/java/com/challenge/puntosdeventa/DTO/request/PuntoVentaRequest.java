package com.challenge.puntosdeventa.DTO.request;

import jakarta.validation.constraints.NotNull;

public record PuntoVentaRequest(

        @NotNull(message = "El Id no puede ser nulo")
        String id,

        @NotNull(message = "El nombre del punto de venta no puede ser nulo")
        String nombre
) {}
