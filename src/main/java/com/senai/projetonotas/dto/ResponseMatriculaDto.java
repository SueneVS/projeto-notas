package com.senai.projetonotas.dto;


public record ResponseMatriculaDto(Long id, Long disciplinaId, String disciplinaNome, Long alunoId, String alunoNome) {
}
