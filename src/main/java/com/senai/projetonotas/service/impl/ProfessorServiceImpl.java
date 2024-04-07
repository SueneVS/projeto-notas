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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        ProfessorEntity novoProfessor = professorRepository.save(new ProfessorEntity(dto.nome()));

        return new ResponseProfessorDto(novoProfessor.getProfessorId(), novoProfessor.getNome());
    }

    @Override
    public List<ProfessorEntity> getEntities() {
        return professorRepository.findAll();
    }

    @Override
    public List<ResponseProfessorDto> getEntitiesDto() {
        List<ProfessorEntity> professores = getEntities();

        return  professores.stream()
                .map(professor -> new ResponseProfessorDto(professor.getProfessorId(), professor.getNome()))
                .collect(Collectors.toList());
    }

    @Override
    public ProfessorEntity getEntity(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new NotFoundException("Não econtrado professor de id: " +id));
    }

    @Override
    public ResponseProfessorDto getEntityDto(Long id) {
        ProfessorEntity professor = getEntity(id);
        return new ResponseProfessorDto(professor.getProfessorId(), professor.getNome());
    }

    @Override
    public ResponseProfessorDto update(Long id, RequestProfessorDto dto) {

        ProfessorEntity professor = getEntity(id);

        if(dto.nome() != null && dto.nome() != professor.getNome()){
            professor.setNome(dto.nome());
        }

        professorRepository.save(professor);
        return new ResponseProfessorDto(professor.getProfessorId(), professor.getNome());
    }

    @Override
    public void delete(Long id) {
        ProfessorEntity professor = getEntity(id);

        long disciplinasProfessor = professor.getDisciplinas().size();

        if (disciplinasProfessor > 0) {
            throw new IllegalStateException("Não é possível excluir o professor porque existem disciplinas associadas a ele.");
        }

        professorRepository.deleteById(id);

    }
}
