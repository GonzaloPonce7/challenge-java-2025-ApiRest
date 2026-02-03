package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
import java.util.List;

public interface IPuntoVentaService {
    List<PuntoVentaResponse> getAll();
    PuntoVentaResponse getById(Long id);
    PuntoVentaResponse create(String nombre);
    PuntoVentaResponse update(Long id, String nuevoNombre);
    void delete(Long id);
    boolean existsPuntoById(Long id);
    boolean existsPuntoByName(String nombre);
}