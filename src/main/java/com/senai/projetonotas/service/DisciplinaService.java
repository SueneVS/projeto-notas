package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;

import java.util.List;

public interface DisciplinaService {


    public void setProfessorService(ProfessorService professorService);
    public ResponseDisciplinaDto create(RequestDisciplinaDto dto);
    public void delete(Long id);
    public ResponseDisciplinaDto update (Long id, RequestDisciplinaDto dto);
    public DisciplinaEntity getEntity (Long id);
    public ResponseDisciplinaDto getEntityDto(Long id);
    public List<DisciplinaEntity> getEntities ();
    public List<ResponseDisciplinaDto> getEntitiesDto ();
    public List<DisciplinaEntity> getEntitiesProfessor(Long id);
    public List<ResponseDisciplinaDto> getEntitiesProfessordto(Long id);

}
