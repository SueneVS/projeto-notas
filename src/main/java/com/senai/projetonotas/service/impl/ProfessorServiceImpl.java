package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.repository.ProfessorRepository;
import com.senai.projetonotas.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {


    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;

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
        return create(professor);
    }

    @Override
    public void delete(Long id) {
        getEntity(id);

        long disciplinasProfessor = disciplinaRepository.countByProfessorProfessorId(id);

        if (disciplinasProfessor > 0) {
            throw new IllegalStateException("Não é possível excluir o professor porque existem disciplinas associadas a ele.");
        }

        professorRepository.deleteById(id);

    }
}
