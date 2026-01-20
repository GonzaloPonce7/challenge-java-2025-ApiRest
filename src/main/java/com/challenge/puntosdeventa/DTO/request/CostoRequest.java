package com.challenge.puntosdeventa.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CostoRequest(

        @NotNull(message = "El punto de venta A no puede ser nulo")
        Long puntoVentaA,

        @NotNull(message = "El punto de venta B no puede ser nulo")
        Long puntoVentaB,

        @NotNull(message = "El costo no puede ser nulo")
        @PositiveOrZero(message = "El costo debe ser mayor o igual a cero")
        Double costo
) {}
