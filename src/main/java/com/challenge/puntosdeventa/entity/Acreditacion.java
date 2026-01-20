package com.challenge.puntosdeventa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "acreditaciones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Acreditacion {

    @Id
    private String id;

    private Double importe;

    @Indexed
    private Long puntoVentaId;

    private String puntoVentaNombre;

    private LocalDateTime fechaRecepcion;
}
