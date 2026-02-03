package com.challenge.puntosdeventa.repository.impl.mongoPersistance;

import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import com.challenge.puntosdeventa.repository.IAcreditacionesRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("mongo")
public interface AcreditacionRepository
        extends MongoRepository<AcreditacionEntity, Integer>,
        IAcreditacionesRepository {
}
