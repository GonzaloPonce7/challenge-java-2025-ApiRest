package com.challenge.puntosdeventa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "acreditaciones")
public record AcreditacionEntity (
        @Id
        Long id,
        Double importe,
        @Indexed
        Long puntoVentaId,
        String puntoVentaNombre,
        LocalDateTime fechaRecepcion
) {}
