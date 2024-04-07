package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.*;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;

import java.util.List;

public interface MatriculaService {
    void setDisciplinaService(DisciplinaService disciplinaService);

    void setAlunoService(AlunoService alunoService);

    public ResponseMatriculaDto create (RequestMatriculaDto dto);
    public void delete(Long id);
    public MatriculaEntity update (Long id,MatriculaEntity dto);
    public MatriculaEntity getEntity (Long id);
    public ResponseMatriculaDto getEntityDto (Long id);
    public List<MatriculaEntity> getEntities ();
    public List<MatriculaEntity> getEntities(Long id);
    public List<MatriculaEntity> getEntitiesDisciplina(Long id);

    List<ResponseMatriculaDto> getEntitiesDisciplinaDto(Long id);

    public List<MatriculaEntity> getEntitiesAluno(Long id);
    public List<ResponseMatriculaDto> getEntitiesAlunoDto(Long id);

    public MediasAlunoDto getMediasAluno(Long id);


    public void updateMediaMatricula(Long id);


}


