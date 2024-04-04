package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.CampoObrigatorioException;
import com.senai.projetonotas.exception.NotFoundException;
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

        if (professor.getNome() == null) {
            throw new CampoObrigatorioException("O campo 'nome' é obrigatório para criar um professor");
        }
        return professorRepository.save(professor);
    }

    @Override
    public List<ProfessorEntity> getEntities() {
        return professorRepository.findAll();
    }

    @Override
    public ProfessorEntity getEntity(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new NotFoundException("Não econtrado professor de id: " +id));
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
