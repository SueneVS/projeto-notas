package com.senai.projetonotas.dto;


public record ResponseMatriculaDto(Long id, double media,Long disciplinaId, String disciplinaNome, Long alunoId, String alunoNome) {
}
