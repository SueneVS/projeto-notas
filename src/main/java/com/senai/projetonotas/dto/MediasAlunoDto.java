package com.senai.projetonotas.dto;

import java.util.List;

public record MediasAlunoDto(List<MediaMatriculaDto> disciplinas, double media) {
}
