package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.DTO.response.AcreditacionResponse;
import java.util.List;

public interface IAcreditacionService {
    AcreditacionResponse procesar(AcreditacionRequest request);
    List<AcreditacionResponse> obtenerTodas();
}