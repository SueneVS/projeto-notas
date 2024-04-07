package com.senai.projetonotas.service;

import com.senai.projetonotas.entity.NotaEntity;

import java.util.List;

public interface NotaService {
    void setMatriculaService(MatriculaService matriculaService);

    void setProfessorService(ProfessorService professorService);

    public NotaEntity create (NotaEntity dto);
    public void delete(Long id);
    public NotaEntity update (Long id,NotaEntity dto);
    public NotaEntity getEntity (Long id);
    public List<NotaEntity> getEntities ();
    public List<NotaEntity> getEntities(Long id);

    public List<NotaEntity> getNotasByMatriculaId(Long id);
}
