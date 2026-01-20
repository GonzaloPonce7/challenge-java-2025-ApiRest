package com.challenge.puntosdeventa.repository;

import com.challenge.puntosdeventa.entity.Acreditacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcreditacionesRepository extends MongoRepository<Acreditacion, String> {
}
