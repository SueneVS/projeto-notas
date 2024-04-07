package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.service.*;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class ColecaoServiceImpl implements ColecaoService{


    public ColecaoServiceImpl(AlunoService alunoService,
                              DisciplinaService disciplinaService,
                              MatriculaService matriculaService,
                              NotaService notaService,
                              ProfessorService professorService
    ) {
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
        this.matriculaService = matriculaService;
        this.notaService = notaService;
        this.professorService = professorService;
    }

    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;
    private final MatriculaService matriculaService;
    private final NotaService notaService;
    private final ProfessorService professorService;

}
