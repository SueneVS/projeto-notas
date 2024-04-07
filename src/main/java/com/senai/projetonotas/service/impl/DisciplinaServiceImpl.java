package com.senai.projetonotas.service.impl;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.repository.ProfessorRepository;
import com.senai.projetonotas.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository Drepository;
    private final ProfessorRepository Prespository;

    @Override
    public DisciplinaEntity create(DisciplinaEntity dto) {

        if (dto.getNome() == null || dto.getProfessor() == null  ) {
            throw new CampoObrigatorioException("Os campos 'nome' e 'professorId' são obrigatório para criar uma disciplina");
        }
        Prespository.findById(dto.getProfessor().getProfessorId()).orElseThrow(() -> new NotFoundException("Não encontrado professor com este id"));

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
        if (dto.getNome() == null || dto.getProfessor() == null  ) {
            throw new CampoObrigatorioException("Os campos 'nome' e 'professorId' são obrigatório ao atualizar uma disciplina");
        }
        Prespository.findById(dto.getProfessor().getProfessorId()).orElseThrow(() -> new NotFoundException("Não encontrado professor com este id"));
        dto.setDisciplinaId(id);
        return Drepository.saveAndFlush(dto);
    }
    public DisciplinaEntity getEntity(Long id) {
        return Drepository.findById(id).orElseThrow(() -> new NotFoundException("Disciplina não encontrada com o id: " + id));
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
