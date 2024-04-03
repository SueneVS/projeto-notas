package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.repository.ProfessorRepository;
import com.senai.projetonotas.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {


    private final ProfessorRepository professorRepository;

    @Override
    public ProfessorEntity create(ProfessorEntity professor) {
        return professorRepository.save(professor);
    }

    @Override
    public List<ProfessorEntity> getEntities() {
        return professorRepository.findAll();
    }

    @Override
    public ProfessorEntity getEntity(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    @Override
    public ProfessorEntity update(Long id, ProfessorEntity professor) {
        getEntity(id);
        professor.setProfessorId(id);
        return professorRepository.save(professor);
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        professorRepository.deleteById(id);

    }
}
