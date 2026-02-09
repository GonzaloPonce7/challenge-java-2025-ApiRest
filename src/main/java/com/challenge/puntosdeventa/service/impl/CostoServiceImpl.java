package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.response.CaminoMinimoResponse;
import com.challenge.puntosdeventa.DTO.response.CostoResponse;
import com.challenge.puntosdeventa.DTO.response.PuntoVentaResponse;
import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;
import com.challenge.puntosdeventa.mapper.CostoMapper;
import com.challenge.puntosdeventa.repository.CostoRepository;
import com.challenge.puntosdeventa.repository.PuntoVentaRepository;
import com.challenge.puntosdeventa.service.ICostoService;
import com.challenge.puntosdeventa.utils.CaminoMinimoCalc;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class CostoServiceImpl implements ICostoService {

    private static final String CACHE_DIRECTOS = "costos::directos";
    private static final String CACHE_GRAFO = "costos::grafos";

    private final PuntoVentaRepository puntoVentaRepo;
    private final CostoRepository costoRepository;
    private final CaminoMinimoCalc caminoMinimoCalc;


    public CostoServiceImpl(PuntoVentaRepository puntoVentaRepo, CostoRepository costoRepository, CaminoMinimoCalc caminoMinimoCalc) {
        this.puntoVentaRepo = puntoVentaRepo;
        this.costoRepository = costoRepository;
        this.caminoMinimoCalc = caminoMinimoCalc;
    }

    @Override
    @CacheEvict(value = {CACHE_DIRECTOS, CACHE_GRAFO}, allEntries = true)
    public void agregarCosto(Long puntoA, Long puntoB, Double costo) {

        if (!puntoVentaRepo.existsById(puntoA) || !puntoVentaRepo.existsById(puntoB)) {
            throw new RuntimeException("No se encontró el punto de venta");
        }

        if (puntoA.equals(puntoB) && costo != 0) {
            throw new IllegalArgumentException("El costo debe ser 0 cuando los puntos son iguales");
        }

        if(costoRepository.existsByIdAAndIdB(puntoA, puntoB) || costoRepository.existsByIdAAndIdB(puntoB, puntoA)){
            throw new IllegalArgumentException("El costo ya existe");
        }

        costoRepository.save(new CostoPuntoVentaEntity(null, puntoA, puntoB, costo));
    }

    @Override
    @CacheEvict(value = {CACHE_DIRECTOS, CACHE_GRAFO}, allEntries = true)
    public void removerCosto(Long puntoA, Long puntoB) {
        costoRepository.deleteByIdAAndIdB(puntoA, puntoB);
        costoRepository.deleteByIdAAndIdB(puntoB, puntoA);
    }

    @Override
    @Cacheable(value = CACHE_DIRECTOS, key = "#puntoVentaId")
    public List<CostoResponse> obtenerCostosDirectos(Long puntoVentaId) {
        if (!puntoVentaRepo.existsById(puntoVentaId)) {
            throw new RuntimeException("No se encontró el punto de venta");
        }

        List<CostoPuntoVentaEntity> costos = costoRepository.findByIdAOrIdB(puntoVentaId, puntoVentaId);

        return costos.stream()
                .map(c -> CostoMapper.toResponseNormalizado(c, puntoVentaId))
                .toList();
    }

    @Override
    @Cacheable(value = CACHE_GRAFO, key = "#origen + '_' + #destino")
    public CaminoMinimoResponse calcularCaminoMinimo(Long origenId, Long destinoId) {
        if (!puntoVentaRepo.existsById(origenId) || !puntoVentaRepo.existsById(destinoId)) {
            throw new RuntimeException("No se encontró el punto de venta");
        }

        List<CostoPuntoVentaEntity> costos = costoRepository.findAll();

        CaminoMinimoCalc.Resultado resultado = caminoMinimoCalc.calcular(origenId, destinoId, costos);

        List<PuntoVentaResponse> camino = resultado.camino().stream()
                .map(id -> {
                    var punto = puntoVentaRepo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Punto no encontrado: " + id));
                    return new PuntoVentaResponse(punto.id(), punto.nombre());
                })
                .toList();

        var puntoOrigen = puntoVentaRepo.findById(origenId).orElseThrow();
        var puntoDestino = puntoVentaRepo.findById(destinoId).orElseThrow();

        return new CaminoMinimoResponse(
                origenId,
                puntoOrigen.nombre(),
                destinoId,
                puntoDestino.nombre(),
                resultado.costoMinimo(),
                camino
        );
    }
}
