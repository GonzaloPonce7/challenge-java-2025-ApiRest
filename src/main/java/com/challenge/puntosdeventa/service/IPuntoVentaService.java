package com.challenge.puntosdeventa.service;

import com.challenge.puntosdeventa.entity.PuntoVenta;
import java.util.List;

public interface IPuntoVentaService {
    List<PuntoVenta> obtenerTodos();
    PuntoVenta obtenerPorId(Long id);
    boolean existe(Long id);
    PuntoVenta crear(String nombre);
    PuntoVenta actualizar(Long id, String nuevoNombre);
    void eliminar(Long id);
}