package com.senai.projetonotas.dto;

import java.time.LocalDate;

public record ResponseAlunoDto(Long id, String nome, LocalDate data) {
}
