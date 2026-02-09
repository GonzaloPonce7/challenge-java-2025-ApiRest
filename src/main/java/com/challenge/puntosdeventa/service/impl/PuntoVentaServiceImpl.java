package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.request.PuntoVentaRequest;
import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
import com.challenge.puntosdeventa.entity.PuntoVentaEntity;
import com.challenge.puntosdeventa.exception.PuntoVentaNotFoundException;
import com.challenge.puntosdeventa.mapper.PuntoVentaMapper;
import com.challenge.puntosdeventa.repository.PuntoVentaRepository;
import com.challenge.puntosdeventa.service.IPuntoVentaService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuntoVentaServiceImpl implements IPuntoVentaService {

    private static final String CACHE_ALL = "puntosVenta::all";
    private static final String CACHE_BY_ID = "puntoVenta::byId";

    private final PuntoVentaRepository repository;

    public PuntoVentaServiceImpl(PuntoVentaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = CACHE_ALL)
    public List<PuntoVentaResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(PuntoVentaMapper::toResponse)
                .toList();
    }

    @Override
    @Cacheable(value = CACHE_BY_ID, key = "#id")
    public PuntoVentaResponse getById(Long id) {
        PuntoVentaEntity entity = repository.findById(id)
                .orElseThrow(() -> new PuntoVentaNotFoundException(id));
        return PuntoVentaMapper.toResponse(entity);
    }

    @CacheEvict(value = CACHE_BY_ID, allEntries = true)
    public PuntoVentaResponse create(PuntoVentaRequest request) {

        if(repository.existsByNombre(request.nombre())) {
            throw new RuntimeException("Punto de venta existente");
        }

        return PuntoVentaMapper.toResponse(repository.save(PuntoVentaMapper.toEntity(request)));
    }

    @Override
    @CacheEvict(value = {CACHE_ALL, CACHE_BY_ID}, allEntries = true)
    public PuntoVentaResponse update(Long id, PuntoVentaRequest request) {

        PuntoVentaEntity entity = repository.findById(id)
                .orElseThrow(() -> new PuntoVentaNotFoundException(id));

        PuntoVentaEntity updated = PuntoVentaMapper.updateEntity(entity, request);
        PuntoVentaEntity saved = repository.save(updated);

        return PuntoVentaMapper.toResponse(saved);
    }

    @Override
    @CacheEvict(value = {CACHE_ALL, CACHE_BY_ID}, allEntries = true)
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new PuntoVentaNotFoundException(id);
        }

        repository.deleteById(id);
    }
}