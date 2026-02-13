package com.challenge.puntosdeventa.mapper;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.DTO.response.AcreditacionResponse;
import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import com.challenge.puntosdeventa.entity.PuntoVentaEntity;

import java.time.LocalDateTime;

public class AcreditacionMapper {

    private AcreditacionMapper() {}

    public static AcreditacionEntity toEntity(AcreditacionRequest request, PuntoVentaEntity puntoVenta, LocalDateTime fechaRecepcion) {
        return new AcreditacionEntity(
                null,
                request.importe(),
                request.puntoVentaId(),
                puntoVenta.nombre(),
                fechaRecepcion
        );
    }

    public static AcreditacionResponse toResponse(AcreditacionEntity entity) {
        return new AcreditacionResponse(
                entity.id(),
                entity.importe(),
                entity.puntoVentaId(),
                entity.puntoVentaNombre(),
                entity.fechaRecepcion()
        );
    }
}