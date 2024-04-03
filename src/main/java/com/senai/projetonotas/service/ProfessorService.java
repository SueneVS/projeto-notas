package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;
import com.senai.projetonotas.entity.ProfessorEntity;

import java.util.List;

public interface ProfessorService {
    public ProfessorEntity create (ProfessorEntity professor);
    public List<ProfessorEntity> getEntities();
    public ProfessorEntity getEntity (Long id);
    public ProfessorEntity update (Long id,ProfessorEntity professor);
    public void delete(Long id);
}
