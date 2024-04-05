package com.senai.projetonotas.exception;

import com.senai.projetonotas.dto.ErroDto;
import com.senai.projetonotas.exception.customException.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionAdvice {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("500")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(500).body(erroDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handler(NotFoundException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("404")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erroDto);
    }

    @ExceptionHandler(CampoObrigatorioException.class)
    public ResponseEntity<?> handle(CampoObrigatorioException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erroDto);
    }

    @ExceptionHandler(MatriculaDuplicadaException.class)
    public ResponseEntity<?> handle(MatriculaDuplicadaException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("409")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(409).body(erroDto);
    }
    @ExceptionHandler(ProfessorNaoAssociadoException.class)
    public ResponseEntity<?> handle(ProfessorNaoAssociadoException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erroDto);
    }
    @ExceptionHandler(MatriculaComNotaCadastradaException.class)
    public ResponseEntity<?> handle(MatriculaComNotaCadastradaException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erroDto);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handler(DataIntegrityViolationException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erroDto);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handler(IllegalStateException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("409")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(409).body(erroDto);
    }
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<?> handler(UnsupportedOperationException e) {
        ErroDto erroDto = ErroDto.builder()
                .codigo("403")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(403).body(erroDto);
    }

}
