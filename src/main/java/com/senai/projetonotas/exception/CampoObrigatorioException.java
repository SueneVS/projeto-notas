package com.senai.projetonotas.exception;

public class CampoObrigatorioException extends RuntimeException {

    public CampoObrigatorioException() {
    }
    public CampoObrigatorioException(String message) {
        super(message);
    }
}
