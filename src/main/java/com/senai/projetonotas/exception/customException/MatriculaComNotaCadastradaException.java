package com.senai.projetonotas.exception.customException;

public class MatriculaComNotaCadastradaException extends RuntimeException{
    public MatriculaComNotaCadastradaException() {
    }

    public MatriculaComNotaCadastradaException(String message) {
        super(message);
    }
}
