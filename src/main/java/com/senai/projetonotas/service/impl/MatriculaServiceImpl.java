package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.repository.AlunoRepository;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.repository.MatriculaRepository;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private final DisciplinaRepository Drepository;
    private final AlunoRepository Arepository;
    @Override
    public MatriculaEntity create(MatriculaEntity dto) {
    Arepository.findById(dto.getAluno().getAlunoId()).orElseThrow(() -> new RuntimeException("Error"));
    Drepository.findById(dto.getDisciplina().getDisciplinaId()).orElseThrow(() -> new RuntimeException("Error"));

    return repository.save(dto);
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        repository.deleteById(id);
    }

    @Override
    public MatriculaEntity update(Long id, MatriculaEntity dto) {
        getEntity(id);
        dto.setMatriculaId(id);
        return repository.saveAndFlush(dto);
    }

    @Override
    public MatriculaEntity getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @Override
    public List<MatriculaEntity> getEntities() {
        return repository.findAll();
    }

    @Override
    public List<MatriculaEntity> getEntities(Long id) {
        return repository.findAll();
    }
}
