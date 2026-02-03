package com.challenge.puntosdeventa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "costoPuntoVenta")
@CompoundIndex(
        name = "idx_idA_idB",
        def = "{'idA': 1, 'idB': 1}",
        unique = true
)
public record CostoPuntoVentaEntity(
        @Id
        Long id,
        @Indexed
        Long idA,
        @Indexed
        Long idB,
        Double costo
) {}
