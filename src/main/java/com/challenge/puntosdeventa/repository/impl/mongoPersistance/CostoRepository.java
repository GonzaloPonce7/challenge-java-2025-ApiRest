package com.challenge.puntosdeventa.repository.impl.mongoPersistance;

import com.challenge.puntosdeventa.entity.CostoPuntoVentaEntity;
import com.challenge.puntosdeventa.repository.ICostoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("mongo")
public interface CostoRepository
        extends MongoRepository<CostoPuntoVentaEntity, String>,
        ICostoRepository {
}
