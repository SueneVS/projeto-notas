package com.senai.projetonotas.exception.customException;

public class MatriculaDuplicadaException extends RuntimeException {

    public MatriculaDuplicadaException() {
    }

    public MatriculaDuplicadaException(String message) {
        super(message);
    }
}
