package com.senai.projetonotas.exception;

public class MatriculaDuplicadaException extends RuntimeException {

    public MatriculaDuplicadaException() {
    }

    public MatriculaDuplicadaException(String message) {
        super(message);
    }
}
