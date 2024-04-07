package com.senai.projetonotas.dto;

import java.time.LocalDate;

public record ResponseNovoAlunoDto(Long id, String nome, LocalDate data) {
}
