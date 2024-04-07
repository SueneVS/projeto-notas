package com.senai.projetonotas.dto;


import lombok.NonNull;
import org.springframework.lang.NonNullApi;

import javax.validation.Valid;


public record CreateAlunoDto(String nome, String dataNascimento) {
}
