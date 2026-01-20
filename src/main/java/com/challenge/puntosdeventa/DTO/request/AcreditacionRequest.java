package com.challenge.puntosdeventa.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AcreditacionRequest(

        @NotNull(message = "El importe no puede ser nulo")
        @Positive(message = "El importe debe ser mayor a cero")
        Double importe,

        @NotNull(message = "El punto de venta no puede ser nulo")
        Long puntoVentaId
) {}
