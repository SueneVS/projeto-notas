package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.RequestProfessorDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.ResponseProfessorDto;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.repository.ProfessorRepository;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.service.ProfessorService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public ResponseProfessorDto create(RequestProfessorDto dto) {

        if (dto.nome() == null) {
            throw new CampoObrigatorioException("O campo 'nome' é obrigatório para criar um professor");
        }
        log.info("Criando professor-> Salvo com sucesso");
        ProfessorEntity professor = professorRepository.save(new ProfessorEntity(dto.nome()));
        log.debug("Criando disciplina -> Registro Salvo: \n{}\n", JsonUtil.objetoParaJson(professor.toString()));
        log.info("transformando a professor em DTO");
        return new ResponseProfessorDto(professor.getProfessorId(), professor.getNome());
    }

    @Override
    public List<ProfessorEntity> getEntities() {
        log.info("Buscando todos os professor");
        return professorRepository.findAll();
    }

    @Override
    public List<ResponseProfessorDto> getEntitiesDto() {
        List<ProfessorEntity> professores = getEntities();

        log.info("Transformando os professores em dto");
        return  professores.stream()
                .map(professor -> new ResponseProfessorDto(professor.getProfessorId(), professor.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorEntity getEntity(Long id) {
        log.info("Buscando professor por id ({})", id);
        return professorRepository.findById(id).orElseThrow(() -> new NotFoundException("Não econtrado professor de id: " +id));
    }

    @Override
    public ResponseProfessorDto getEntityDto(Long id) {
        ProfessorEntity professor = getEntity(id);
        log.info("transformando o professor em DTO");
        return new ResponseProfessorDto(professor.getProfessorId(), professor.getNome());
    }

    @Override
    public ResponseProfessorDto update(Long id, RequestProfessorDto dto) {

        ProfessorEntity professor = getEntity(id);

        if(dto.nome() != null && dto.nome() != professor.getNome()){
            professor.setNome(dto.nome());
        }
        professorRepository.save(professor);
        log.info("Criando professor-> Salvo com sucesso");
        log.debug("Criando professor -> Registro Salvo: \n{}\n", JsonUtil.objetoParaJson(professor.toString()));

        log.info("transformando a professor em DTO");
        return new ResponseProfessorDto(professor.getProfessorId(), professor.getNome());
    }

    @Override
    public void delete(Long id) {

        ProfessorEntity professor = getEntity(id);

        long disciplinasProfessor = professor.getDisciplinas().size();

        log.info("Valida se professor ja esta viculado a alguma disciplina");
        if (disciplinasProfessor > 0) {
            throw new IllegalStateException("Não é possível excluir o professor porque existem disciplinas associadas a ele.");
        }
        log.info("Excluindo disciplina com id ({}) -> Excluindo", id);
        professorRepository.deleteById(id);
        log.info("Excluindo disciplina com id ({}) -> Excluído com sucesso", id);

    }
}
