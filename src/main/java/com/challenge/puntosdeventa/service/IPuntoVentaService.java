package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.DTO.request.PuntoVentaRequest;
import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
import java.util.List;

public interface IPuntoVentaService {
    List<PuntoVentaResponse> getAll();
    PuntoVentaResponse getById(Long id);
    PuntoVentaResponse create(PuntoVentaRequest request);
    PuntoVentaResponse update(Long id, PuntoVentaRequest request);
    void delete(Long id);
}