package com.senai.projetonotas.exception;

public class ProfessorNaoAssociadoException extends RuntimeException{

    public ProfessorNaoAssociadoException() {
    }

    public ProfessorNaoAssociadoException(String message) {
        super(message);
    }
}
