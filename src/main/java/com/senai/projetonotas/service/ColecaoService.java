package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.RequestProfessorDto;
import com.senai.projetonotas.dto.ResponseProfessorDto;
import com.senai.projetonotas.entity.ProfessorEntity;

import java.util.List;

public interface ColecaoService {

    public AlunoService getAlunoService();

    public DisciplinaService getDisciplinaService();

    public MatriculaService getMatriculaService();

    public NotaService getNotaService();

    public ProfessorService getProfessorService();

}
