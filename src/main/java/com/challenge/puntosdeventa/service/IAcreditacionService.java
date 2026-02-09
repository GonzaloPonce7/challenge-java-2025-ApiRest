package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.DTO.response.AcreditacionResponse;
import java.util.List;

public interface IAcreditacionService {
    AcreditacionResponse save(AcreditacionRequest request);
    List<AcreditacionResponse> getAll();
}