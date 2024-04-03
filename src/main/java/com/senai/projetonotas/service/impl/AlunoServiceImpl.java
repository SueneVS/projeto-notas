package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.repository.AlunoRepository;
import com.senai.projetonotas.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository repository;
    @Override
    public AlunoEntity create(AlunoEntity dto) {
        return repository.save(dto);
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        repository.deleteById(id);
    }

    @Override
    public AlunoEntity update(Long id, AlunoEntity dto) {
        getEntity(id);
        dto.setAlunoId(id);
        return repository.saveAndFlush(dto);
    }

    @Override
    public AlunoEntity getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @Override
    public List<AlunoEntity> getEntities() {
        return repository.findAll();
    }

    @Override
    public List<AlunoEntity> getEntities(Long id) {
        return repository.findAll();
    }
}
