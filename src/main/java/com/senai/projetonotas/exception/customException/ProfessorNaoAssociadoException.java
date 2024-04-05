package com.senai.projetonotas.exception.customException;

public class ProfessorNaoAssociadoException extends RuntimeException{

    public ProfessorNaoAssociadoException() {
    }

    public ProfessorNaoAssociadoException(String message) {
        super(message);
    }
}
