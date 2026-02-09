package com.challenge.puntosdeventa.repository;

import com.challenge.puntosdeventa.entity.PuntoVentaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PuntoVentaRepository extends MongoRepository<PuntoVentaEntity, Long> {
    List<PuntoVentaEntity> findAll();
    Optional<PuntoVentaEntity> findById(Long id);
    Optional<PuntoVentaEntity> findByNombre(String nombre);
    PuntoVentaEntity save(PuntoVentaEntity puntoVenta);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNombre(String nombre);
}