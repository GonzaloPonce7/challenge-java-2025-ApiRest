package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.DTO.response.AcreditacionResponse;
import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import com.challenge.puntosdeventa.repository.AcreditacionRepository;
import com.challenge.puntosdeventa.service.IAcreditacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcreditacionServiceImpl implements IAcreditacionService {

    private final AcreditacionRepository repository;
    private final PuntoVentaServiceImpl puntoVentaServiceImpl;


    @Override
    public AcreditacionResponse save(AcreditacionRequest request) {
        return null;
    }

    @Override
    public List<AcreditacionResponse> getAll() {
        return List.of();
    }
}