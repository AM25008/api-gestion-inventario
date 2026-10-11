package com.grupo7.GestionInventarioAPI.exception;

public class AccesoNoAutorizadoException extends RuntimeException {
    public AccesoNoAutorizadoException(String message) {
        super(message);
    }
}
