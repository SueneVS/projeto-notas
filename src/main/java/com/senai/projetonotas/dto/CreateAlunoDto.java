package com.senai.projetonotas.dto;

import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

public record CreateAlunoDto(@Validated String nome, @Validated String dataNascimento) {
}
