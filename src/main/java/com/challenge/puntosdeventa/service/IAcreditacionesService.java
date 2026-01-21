package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.entity.Acreditacion;
import java.util.List;

public interface IAcreditacionesService {
    Acreditacion procesar(AcreditacionRequest request);
    List<Acreditacion> obtenerTodas();
}