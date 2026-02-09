package com.challenge.puntosdeventa.repository;

import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AcreditacionRepository extends MongoRepository<AcreditacionEntity, String> {
    List<AcreditacionEntity> findAll();
    Optional<AcreditacionEntity> findById(String id);
    AcreditacionEntity save(AcreditacionEntity acreditacion);
    List<AcreditacionEntity> findByPuntoVentaId(Integer puntoVentaId);
    List<AcreditacionEntity> findByFechaRecepcionBetween(LocalDateTime inicio, LocalDateTime fin);
    long countByPuntoVentaId(Integer puntoVentaId);
}
