package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.DTO.response.CaminoMinimoResponse;
import com.challenge.puntosdeventa.DTO.response.CostoResponse;

import java.util.List;

public interface ICostoService {
    void agregarCosto(Long puntoA, Long puntoB, Double costo);
    void removerCosto(Long puntoA, Long puntoB);
    List<CostoResponse> obtenerCostosDirectos(Long puntoVentaId);
    CaminoMinimoResponse calcularCaminoMinimo(Long origen, Long destino);
}