package com.challenge.puntosdeventa.service.impl;

import com.challenge.puntosdeventa.DTO.request.AcreditacionRequest;
import com.challenge.puntosdeventa.entity.Acreditacion;
import com.challenge.puntosdeventa.entity.PuntoVenta;
import com.challenge.puntosdeventa.repository.AcreditacionesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcreditacionesServiceImpl {

    private final AcreditacionesRepository repository;
    private final PuntoVentaServiceImpl puntoVentaServiceImpl;

    /**
     * Procesa una acreditación: la enriquece y la persiste
     */
    public Acreditacion procesar(AcreditacionRequest request) {
        // Validar que el punto de venta exista
        PuntoVenta puntoVenta = puntoVentaServiceImpl.obtenerPorId(request.puntoVentaId());

        // Crear acreditación enriquecida
        Acreditacion acreditacion = new Acreditacion();
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
    public List<Acreditacion> obtenerTodas() {
        return repository.findAll();
    }
}