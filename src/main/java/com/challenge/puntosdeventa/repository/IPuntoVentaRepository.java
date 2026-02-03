package com.challenge.puntosdeventa.repository;

import com.challenge.puntosdeventa.entity.PuntoVentaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPuntoVentaRepository {
    List<PuntoVentaEntity> findAll();
    Optional<PuntoVentaEntity> findById(Long id);
    PuntoVentaEntity save(PuntoVentaEntity puntoVenta);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNombre(String nombre);
    Optional<PuntoVentaEntity> findByNombre(String nombre);
}