package com.challenge.puntosdeventa.exception;

public class PuntoVentaNotFoundException extends RuntimeException {
    public PuntoVentaNotFoundException(Long id) {
        super("Punto de venta no encontrado con id: " + id);
    }
}