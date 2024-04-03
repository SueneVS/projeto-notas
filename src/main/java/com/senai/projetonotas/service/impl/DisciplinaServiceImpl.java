package com.senai.projetonotas.service.impl;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository Drepository;
    //private final ProfessorRepository Prespository;

    @Override
    public DisciplinaEntity create(DisciplinaEntity dto) {
        //Prespository.findById(dto.getProfessor().getProfessorId()).orElseThrow(() -> new RuntimeException("Error"));
        return Drepository.save(dto);
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        Drepository.deleteById(id);
    }

    @Override
    public DisciplinaEntity update(Long id, DisciplinaEntity dto) {
        getEntity(id);
        dto.setDisciplinaId(id);
        return Drepository.saveAndFlush(dto);
    }
    public DisciplinaEntity getEntity(Long id) {
        return Drepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @Override
    public List<DisciplinaEntity> getEntities() {
        return Drepository.findAll();
    }


    @Override
    public List<MatriculaEntity> getEntitiesProfessor(Long id) {
        return Drepository.findAllByProfessorProfessorId(id);
    }
}

