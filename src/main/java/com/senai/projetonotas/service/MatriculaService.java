package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;
import com.senai.projetonotas.entity.MatriculaEntity;

import java.util.List;

public interface MatriculaService {
    public MatriculaEntity create (MatriculaEntity dto);
    public void delete(Long id);
    public MatriculaEntity update (Long id,MatriculaEntity dto);
    public MatriculaEntity getEntity (Long id);
    public List<MatriculaEntity> getEntities ();
    public List<MatriculaEntity> getEntities(Long id);
    public List<MatriculaEntity> getEntitiesDisciplina(Long id);
    public List<MatriculaEntity> getEntitiesAluno(Long id);
}
