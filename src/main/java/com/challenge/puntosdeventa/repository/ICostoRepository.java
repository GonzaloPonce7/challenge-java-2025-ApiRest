package com.challenge.puntosdeventa.repository;

import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICostoRepository {
    List<CostoPuntoVentaEntity> findAll();
    Optional<CostoPuntoVentaEntity> findById(Long id);
    Optional<CostoPuntoVentaEntity> findByIdAAndIdB(Long idA, Long idB);
    List<CostoPuntoVentaEntity> findByIdAOrIdB(Long idA, Long idB);
    List<CostoPuntoVentaEntity> findByPuntoId(Long puntoId);
    CostoPuntoVentaEntity save(CostoPuntoVentaEntity costo);
    void deleteById(Long id);
    void deleteByIdAAndIdB(Long idA, Long idB);
    boolean existsByIdAAndIdB(Long idA, Long idB);
}