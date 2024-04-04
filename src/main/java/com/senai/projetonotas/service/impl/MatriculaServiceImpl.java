package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;
import com.senai.projetonotas.dto.MediaMatriculaDto;
import com.senai.projetonotas.dto.MediasAlunoDto;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.exception.CampoObrigatorioException;
import com.senai.projetonotas.exception.MatriculaDuplicadaException;
import com.senai.projetonotas.exception.NotFoundException;
import com.senai.projetonotas.repository.AlunoRepository;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.repository.MatriculaRepository;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private final DisciplinaRepository Drepository;
    private final AlunoRepository Arepository;

    @Override
    public MatriculaEntity create(MatriculaEntity dto) {

        if (dto.getAluno() == null || dto.getDisciplina() == null) {
            throw new CampoObrigatorioException("Os campos 'alunoId' e 'disciplinaId' são obrigatório para criar uma matricula");
        }

        Arepository.findById(dto.getAluno().getAlunoId()).orElseThrow(() -> new NotFoundException("Não encontrado aluno com este id"));
        Drepository.findById(dto.getDisciplina().getDisciplinaId()).orElseThrow(() -> new NotFoundException("Não encontrada disciplina com este id"));

        if (repository.existsByAluno_AlunoIdAndDisciplina_DisciplinaId(dto.getAluno().getAlunoId(), dto.getDisciplina().getDisciplinaId())) {
            throw new MatriculaDuplicadaException("O aluno já está matriculado nesta disciplina.");
        }

    return repository.save(dto);
    }


    @Override
    public void delete(Long id) {
        getEntity(id);
        repository.deleteById(id);
    }

    @Override
    public MatriculaEntity update(Long id, MatriculaEntity dto) {
        getEntity(id);
        dto.setMatriculaId(id);
        return repository.saveAndFlush(dto);
    }

    @Override
    public MatriculaEntity getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrada matricula com id: " +id));
    }

    @Override
    public List<MatriculaEntity> getEntities() {
        return repository.findAll();
    }

    @Override
    public List<MatriculaEntity> getEntities(Long id) {
        return repository.findAll();
    }

    @Override
    public List<MatriculaEntity> getEntitiesDisciplina(Long id) {
        return repository.findAllByDisciplinaDisciplinaId(id);
    }

    @Override
    public List<MatriculaEntity> getEntitiesAluno(Long id) {
        return repository.findAllByAlunoAlunoId(id);
    }

    @Override
    public MediasAlunoDto getMediasAluno(Long id){
        List<MatriculaEntity> matriculas = getEntitiesAluno(id);
        double notas =0.0;
        List<MediaMatriculaDto> mediaMaticula =new ArrayList<>();

        for(MatriculaEntity maticula: matriculas){
            mediaMaticula.add(new MediaMatriculaDto(maticula.getDisciplina().getNome(), maticula.getMediaFinal()));
            notas += maticula.getMediaFinal();
        }
        return  new MediasAlunoDto(mediaMaticula,(notas/matriculas.size()));
    }
}
