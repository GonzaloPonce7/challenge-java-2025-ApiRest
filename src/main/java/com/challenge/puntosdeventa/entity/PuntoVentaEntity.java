package com.challenge.puntosdeventa.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "puntoVenta")
public record PuntoVentaEntity(
        @Id
        Long id,
        String nombre
) {}
