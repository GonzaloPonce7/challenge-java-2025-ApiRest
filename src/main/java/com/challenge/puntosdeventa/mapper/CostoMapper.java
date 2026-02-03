package com.challenge.puntosdeventa.mapper;

import com.challenge.puntosdeventa.DTO.request.CostoRequest;
import com.challenge.puntosdeventa.DTO.response.CostoResponse;
import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CostoMapper {

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
}