package com.senai.projetonotas.exception.customException;

public class CampoObrigatorioException extends RuntimeException {

    public CampoObrigatorioException() {
    }
    public CampoObrigatorioException(String message) {
        super(message);
    }
}
