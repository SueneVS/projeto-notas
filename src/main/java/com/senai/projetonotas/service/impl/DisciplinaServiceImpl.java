package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository repository;
    private final ProfessorService professorService;

    @Override
    public ResponseDisciplinaDto create(RequestDisciplinaDto dto) {
        if (dto.nome() == null
                || dto.nome().isEmpty()
                || dto.professorId() == null
        ) {
            ArrayList<String> erros = new ArrayList<>();
            if (dto.nome() == null) {
                erros.add("O campo 'nome' é obrigadorio");
            }
            if (dto.professorId() == null) {
                erros.add("O campo 'professorId' é obrigadorio");
            }
            throw new CampoObrigatorioException(erros.toString());
        }
        ProfessorEntity professor =  professorService.getEntity(dto.professorId());

        DisciplinaEntity disciplina = repository.save(new DisciplinaEntity(dto.nome(), professor));

        return new ResponseDisciplinaDto(disciplina.getDisciplinaId(),disciplina.getNome());
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        repository.deleteById(id);
    }

    @Override
    public DisciplinaEntity update(Long id, DisciplinaEntity dto) {
        getEntity(id);
        dto.setDisciplinaId(id);
        return repository.saveAndFlush(dto);
    }

    public DisciplinaEntity getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
    }

    @Override
    public List<DisciplinaEntity> getEntities() {
        return repository.findAll();
    }


    @Override
    public List<MatriculaEntity> getEntitiesProfessor(Long id) {
        return repository.findAllByProfessorProfessorId(id);
    }
}

