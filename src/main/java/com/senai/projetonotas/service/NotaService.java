package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.RequestNotaDto;
import com.senai.projetonotas.dto.ResponseNotaDto;
import com.senai.projetonotas.entity.NotaEntity;

import java.util.List;

public interface NotaService {
    void setMatriculaService(MatriculaService matriculaService);

    void setProfessorService(ProfessorService professorService);

    public ResponseNotaDto create (RequestNotaDto dto);
    public void delete(Long id);

    List<ResponseNotaDto> getNotasByMatriculaIdDto(Long matriculaId);

    public NotaEntity update (Long id, NotaEntity dto);
    public NotaEntity getEntity (Long id);
    public List<NotaEntity> getEntities ();
    public List<NotaEntity> getEntities(Long id);

    public List<NotaEntity> getNotasByMatriculaId(Long id);
}
