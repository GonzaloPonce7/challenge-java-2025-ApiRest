package com.challenge.puntosdeventa.exception;

public class CaminoNoEncontradoException extends RuntimeException {
    public CaminoNoEncontradoException(Long origen, Long destino) {
        super("No existe camino entre el punto " + origen + " y el punto " + destino);
    }
}