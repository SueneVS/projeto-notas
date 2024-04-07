package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.*;
import com.senai.projetonotas.entity.MatriculaEntity;

import java.util.List;

public interface MatriculaService {
    void setDisciplinaService(DisciplinaService disciplinaService);

    void setAlunoService(AlunoService alunoService);

    public ResponseMatriculaDto create (RequestMatriculaDto dto);
    public void delete(Long id);
    public MatriculaEntity getEntity (Long id);
    public ResponseMatriculaDto getEntityDto (Long id);
    public List<MatriculaEntity> getEntities ();

    public List<ResponseMatriculaDto> getEntitiesDtos();

    public List<MatriculaEntity> getEntitiesDisciplina(Long id);

    List<ResponseMatriculaDto> getEntitiesDisciplinaDto(Long id);

    public List<MatriculaEntity> getEntitiesAluno(Long id);
    public List<ResponseMatriculaDto> getEntitiesAlunoDto(Long id);

    public MediaAlunoDto getMediaAluno(Long id);


    public void updateMediaMatricula(Long id);


}


