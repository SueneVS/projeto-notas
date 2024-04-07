package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.CreateAlunoDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.UpdateAlunoDto;
import com.senai.projetonotas.entity.AlunoEntity;

import java.util.List;

public interface AlunoService {
    public ResponseAlunoDto create (CreateAlunoDto dto);
    public void delete(Long id);
    public ResponseAlunoDto update (Long id, UpdateAlunoDto dto);
    public AlunoEntity getEntity (Long id);
    public ResponseAlunoDto getEntityDto (Long id);
    public List<AlunoEntity> getEntities ();
    public List<AlunoEntity> getEntities(Long id);
}
