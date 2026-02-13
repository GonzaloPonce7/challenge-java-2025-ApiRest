package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.DTO.response.AcreditacionResponse;
import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import com.challenge.puntosdeventa.entity.PuntoVentaEntity;
import com.challenge.puntosdeventa.exception.PuntoVentaNotFoundException;
import com.challenge.puntosdeventa.mapper.AcreditacionMapper;
import com.challenge.puntosdeventa.repository.AcreditacionRepository;
import com.challenge.puntosdeventa.repository.PuntoVentaRepository;
import com.challenge.puntosdeventa.service.IAcreditacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcreditacionServiceImpl implements IAcreditacionService {

    private final AcreditacionRepository repository;
    private final PuntoVentaRepository puntoVentaRepo;

    @Override
    public AcreditacionResponse save(AcreditacionRequest request) {
        PuntoVentaEntity puntoVenta = puntoVentaRepo.findById(request.puntoVentaId())
                .orElseThrow(() -> new PuntoVentaNotFoundException(request.puntoVentaId()));

        AcreditacionEntity entity = AcreditacionMapper.toEntity(
                request,
                puntoVenta,
                LocalDateTime.now()
        );

        AcreditacionEntity saved = repository.save(entity);
        return AcreditacionMapper.toResponse(saved);
    }

    @Override
    public List<AcreditacionResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(AcreditacionMapper::toResponse)
                .toList();
    }
}
