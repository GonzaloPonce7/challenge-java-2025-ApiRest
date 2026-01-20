package com.challenge.puntosdeventa.exception;

public class PuntoVentaDuplicadoException extends RuntimeException {
    public PuntoVentaDuplicadoException(String nombre) {
        super("Ya existe un punto de venta con nombre: " + nombre);
    }
}