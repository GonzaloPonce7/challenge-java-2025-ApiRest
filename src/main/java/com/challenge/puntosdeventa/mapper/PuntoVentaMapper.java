package com.challenge.puntosdeventa.mapper;

import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
import com.challenge.puntosdeventa.entity.PuntoVentaEntity;
import com.challenge.puntosdeventa.DTO.request.PuntoVentaRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PuntoVentaMapper {

    public static PuntoVentaEntity toEntity(PuntoVentaRequest request) {
        return new PuntoVentaEntity(
                request.id(),
                request.nombre()
        );
    }

    public static PuntoVentaResponse toResponse(PuntoVentaEntity entity) {
        return new PuntoVentaResponse(
                entity.id(),
                entity.nombre()
        );
    }

    public static PuntoVentaEntity updateEntity(PuntoVentaEntity entity, PuntoVentaRequest request) {
        return new PuntoVentaEntity(
                entity.id(),
                request.nombre()
        );
    }
}