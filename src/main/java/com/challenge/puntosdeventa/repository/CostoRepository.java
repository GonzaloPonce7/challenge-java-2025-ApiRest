package com.challenge.puntosdeventa.repository;

import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostoRepository extends MongoRepository<CostoPuntoVentaEntity, String> {
    List<CostoPuntoVentaEntity> findAll();
    List<CostoPuntoVentaEntity> findByIdAOrIdB(Long idA, Long idB);
    CostoPuntoVentaEntity save(CostoPuntoVentaEntity costo);
    void deleteByIdAAndIdB(Long idA, Long idB);
    boolean existsByIdAAndIdB(Long idA, Long idB);
}