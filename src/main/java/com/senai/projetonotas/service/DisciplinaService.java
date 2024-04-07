package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;
import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;

import java.util.List;

public interface DisciplinaService {
    public ResponseDisciplinaDto create(RequestDisciplinaDto dto);
    public void delete(Long id);
    public ResponseDisciplinaDto update (Long id, RequestDisciplinaDto dto);
    public DisciplinaEntity getEntity (Long id);
    public ResponseDisciplinaDto getEntityDto(Long id);
    public List<DisciplinaEntity> getEntities ();
    public List<MatriculaEntity> getEntitiesProfessor(Long id);

}
