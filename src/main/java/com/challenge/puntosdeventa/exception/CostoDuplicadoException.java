package com.challenge.puntosdeventa.exception;

public class CostoDuplicadoException extends RuntimeException {
    public CostoDuplicadoException(Long puntoA, Long puntoB) {
        super("Ya existe un costo entre los puntos " + puntoA + " y " + puntoB);
    }
}
