package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

        return new ResponseDisciplinaDto(
                disciplina.getDisciplinaId(),
                disciplina.getNome(),
                disciplina.getProfessor().getProfessorId(),
                disciplina.getProfessor().getNome()
        );
    }

    @Override
    public void delete(Long id) {
        DisciplinaEntity disciplina = getEntity(id);
        repository.delete(disciplina);
    }

    @Override
    public ResponseDisciplinaDto update(Long id, RequestDisciplinaDto dto) {
        DisciplinaEntity disciplina = getEntity(id);
        disciplina.setNome(dto.nome());
        if(dto.professorId() != null){
            ProfessorEntity professor =  professorService.getEntity(dto.professorId());
            disciplina.setProfessor(professor);
        }
        repository.save(disciplina);
        return new ResponseDisciplinaDto(
                disciplina.getDisciplinaId(),
                disciplina.getNome(),
                disciplina.getProfessor().getProfessorId(),
                disciplina.getProfessor().getNome()
        );
    }

    @Override
    public DisciplinaEntity getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrada disciplina com id: " +id));
    }


    @Override
    public ResponseDisciplinaDto getEntityDto(Long id) {
        DisciplinaEntity disciplina = getEntity(id);

        return new ResponseDisciplinaDto(
                disciplina.getDisciplinaId(),
                disciplina.getNome(),
                disciplina.getProfessor().getProfessorId(),
                disciplina.getProfessor().getNome()
        );
    }


    @Override
    public List<DisciplinaEntity> getEntities() {
        return repository.findAll();
    }

    @Override
    public List<ResponseDisciplinaDto> getEntitiesDto (){
        List<DisciplinaEntity> disciplinas = getEntities();

        return disciplinas.stream()
                .map(disciplina -> new ResponseDisciplinaDto(
                        disciplina.getDisciplinaId(),
                        disciplina.getNome(),
                        disciplina.getProfessor().getProfessorId(),
                        disciplina.getProfessor().getNome()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MatriculaEntity> getEntitiesProfessor(Long id) {
        return repository.findAllByProfessorProfessorId(id);
    }
}

