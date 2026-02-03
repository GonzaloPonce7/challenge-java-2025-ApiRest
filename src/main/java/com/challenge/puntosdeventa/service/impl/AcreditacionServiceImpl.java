package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.entity.AcreditacionEntity;
import com.challenge.puntosdeventa.repository.impl.mongoPersistance.AcreditacionRepository;
import com.challenge.puntosdeventa.service.IAcreditacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcreditacionServiceImpl implements IAcreditacionService {

    private final AcreditacionRepository repository;
    private final PuntoVentaServiceImpl puntoVentaServiceImpl;

    /**
     * Procesa una acreditación: la enriquece y la persiste
     */
    public AcreditacionEntity procesar(AcreditacionRequest request) {
        // Validar que el punto de venta exista
        PuntoVenta puntoVenta = puntoVentaServiceImpl.getById(request.puntoVentaId());

        // Crear acreditación enriquecida
        AcreditacionEntity acreditacion = new AcreditacionEntity();
        acreditacion.setImporte(request.importe());
        acreditacion.setPuntoVentaId(request.puntoVentaId());
        acreditacion.setPuntoVentaNombre(puntoVenta.nombre());
        acreditacion.setFechaRecepcion(LocalDateTime.now());

        // Persistir en MongoDB
        return repository.save(acreditacion);
    }

    /**
     * Obtiene todas las acreditaciones
     */
    public List<AcreditacionEntity> obtenerTodas() {
        return repository.findAll();
    }
}