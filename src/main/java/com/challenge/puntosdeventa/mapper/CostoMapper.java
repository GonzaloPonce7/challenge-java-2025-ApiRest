package com.challenge.puntosdeventa.mapper;

import com.challenge.puntosdeventa.DTO.request.CostoRequest;
import com.challenge.puntosdeventa.DTO.response.CostoResponse;
import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;

public class CostoMapper {

    private CostoMapper() {}

    public static CostoPuntoVentaEntity toEntity(CostoRequest request) {
        return new CostoPuntoVentaEntity(
                null,
                request.idA(),
                request.idB(),
                request.costo()
        );
    }

    public static CostoResponse toResponse(CostoPuntoVentaEntity entity) {
        return new CostoResponse(
                entity.id(),
                entity.idA(),
                entity.idB(),
                entity.costo()
        );
    }

    public static CostoResponse toResponseNormalizado(CostoPuntoVentaEntity entity, Long puntoReferencia) {
        if (entity.idA().equals(puntoReferencia)) {
            return new CostoResponse(
                    entity.id(),
                    entity.idA(),
                    entity.idB(),
                    entity.costo()
            );
        } else {
            return new CostoResponse(
                    entity.id(),
                    entity.idB(),
                    entity.idA(),
                    entity.costo()
            );
        }
    }
}