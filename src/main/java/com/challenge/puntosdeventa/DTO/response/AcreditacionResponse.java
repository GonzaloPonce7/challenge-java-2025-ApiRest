package com.challenge.puntosdeventa.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record AcreditacionResponse(
        Long id,
        Double importe,
        Long puntoVentaId,
        String puntoVentaNombre,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime fechaRecepcion
) {}