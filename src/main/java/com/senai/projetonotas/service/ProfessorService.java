package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.RequestProfessorDto;
import com.senai.projetonotas.dto.ResponseProfessorDto;
import com.senai.projetonotas.entity.ProfessorEntity;

import java.util.List;

public interface ProfessorService {
    public ResponseProfessorDto create(RequestProfessorDto dto);
    public List<ProfessorEntity> getEntities();
    public ProfessorEntity getEntity (Long id);
    public ResponseProfessorDto getEntityDto(Long id);
    public List<ResponseProfessorDto> getEntitiesDto();
    public ResponseProfessorDto update(Long id, RequestProfessorDto dto);
    public void delete(Long id);
}
