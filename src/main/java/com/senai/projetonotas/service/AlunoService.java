package com.senai.projetonotas.service;

import com.senai.projetonotas.entity.AlunoEntity;

import java.util.List;

public interface AlunoService {
    public AlunoEntity create (AlunoEntity dto);
    public void delete(Long id);
    public AlunoEntity update (Long id, AlunoEntity dto);
    public AlunoEntity getEntity (Long id);
    public List<AlunoEntity> getEntities ();
    public List<AlunoEntity> getEntities(Long id);
}
