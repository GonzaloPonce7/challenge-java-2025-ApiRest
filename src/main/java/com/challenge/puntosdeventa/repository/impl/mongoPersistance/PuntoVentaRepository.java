package com.challenge.puntosdeventa.repository.impl.mongoPersistance;

import com.challenge.puntosdeventa.entity.PuntoVentaEntity;
import com.challenge.puntosdeventa.repository.IPuntoVentaRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;


@Profile("mongo")
public interface PuntoVentaRepository
        extends MongoRepository<PuntoVentaEntity, Integer>,
        IPuntoVentaRepository {
}