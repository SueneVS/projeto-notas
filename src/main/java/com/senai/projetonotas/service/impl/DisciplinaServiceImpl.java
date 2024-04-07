package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.service.ProfessorService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository repository;
    private ProfessorService professorService;

    public DisciplinaServiceImpl(DisciplinaRepository repository) {
        this.repository = repository;

    }

    @Override
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @Override
    public ResponseDisciplinaDto create(RequestDisciplinaDto dto) {
        if (dto.nome() == null
                || dto.nome().isEmpty()
                || dto.professorId() == null
        ) {
            ArrayList<String> erros = new ArrayList<>();
            if (dto.nome() == null) {
                erros.add("O campo 'nome' é obrigatorio");
            }
            if (dto.professorId() == null) {
                erros.add("O campo 'professorId' é obrigatorio");
            }
            throw new CampoObrigatorioException(erros.toString());
        }
        log.info("Buscando professor por id ({}) -> Encontrado", dto.professorId());
        ProfessorEntity professor =  professorService.getEntity(dto.professorId());


        DisciplinaEntity disciplina = repository.save(new DisciplinaEntity(dto.nome(), professor));
        log.info("Criando disciplina-> Salvo com sucesso");

        log.info("transformando a disciplinas em DTO");

        return new ResponseDisciplinaDto(
                disciplina.getDisciplinaId(),
                disciplina.getNome(),
                disciplina.getProfessor().getProfessorId(),
                disciplina.getProfessor().getNome()
        );
    }

    @Override
    public void delete(Long id) {
        log.info("Buscando disciplina por id ({}) -> Encontrado", id);
        DisciplinaEntity disciplina = getEntity(id);
        log.info("Excluindo disciplina com id ({}) -> Excluindo", id);
        repository.delete(disciplina);
        log.info("Excluindo disciplina com id ({}) -> Excluído com sucesso", id);

    }

    @Override
    public ResponseDisciplinaDto update(Long id, RequestDisciplinaDto dto) {
        DisciplinaEntity disciplina = getEntity(id);
        log.info("Alterando disciplina com id ({}) -> Salvar: \n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));

        disciplina.setNome(dto.nome());
        if(dto.professorId() != null){
            ProfessorEntity professor =  professorService.getEntity(dto.professorId());
            disciplina.setProfessor(professor);
        }
        repository.save(disciplina);
        log.info("Alterando disciplina -> Salvo com sucesso");

        log.info("transformando a disciplinas em DTO");
        return new ResponseDisciplinaDto(
                disciplina.getDisciplinaId(),
                disciplina.getNome(),
                disciplina.getProfessor().getProfessorId(),
                disciplina.getProfessor().getNome()
        );
    }

    @Override
    public DisciplinaEntity getEntity(Long id) {
        log.info("Buscando disciplina por id ({})", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrada disciplina com id: " +id));
    }


    @Override
    public ResponseDisciplinaDto getEntityDto(Long id) {
        DisciplinaEntity disciplina = getEntity(id);

        log.info("Buscando disciplina por id ({}) -> Encontrado", id);

        log.info("transformando a disciplinas em DTO");
        return new ResponseDisciplinaDto(
                disciplina.getDisciplinaId(),
                disciplina.getNome(),
                disciplina.getProfessor().getProfessorId(),
                disciplina.getProfessor().getNome()
        );
    }


    @Override
    public List<DisciplinaEntity> getEntities() {
        log.info("Buscando todos as disciplina");
        List<DisciplinaEntity> disciplinas = repository.findAll();

        log.info("Buscando todos os alunos -> {} Encontrados", disciplinas.size());

        return disciplinas;
    }

    @Override
    public List<ResponseDisciplinaDto> getEntitiesDto (){
        List<DisciplinaEntity> disciplinas = getEntities();
        log.info("Transformando as disciplinas em dto");
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
        log.info("Buscando todas as disciplinas do professor com id {}", id);
        return repository.findAllByProfessorProfessorId(id);
    }
}

