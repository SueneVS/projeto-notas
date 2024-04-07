package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.MediaMatriculaDto;
import com.senai.projetonotas.dto.MediasAlunoDto;
import com.senai.projetonotas.dto.RequestMatriculaDto;
import com.senai.projetonotas.dto.ResponseMatriculaDto;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.MatriculaComNotaCadastradaException;
import com.senai.projetonotas.exception.customException.MatriculaDuplicadaException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.AlunoRepository;
import com.senai.projetonotas.repository.DisciplinaRepository;
import com.senai.projetonotas.repository.MatriculaRepository;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private final DisciplinaService disciplinaService;
    private final AlunoService alunoService;

    @Override
    public ResponseMatriculaDto create(RequestMatriculaDto dto) {

        if (dto.disciplinaId() == null || dto.disciplinaId() == null) {
            ArrayList<String> erros = new ArrayList<>();

            if (dto.alunoId() == null) {
                erros.add("O campo 'alunoId' é obrigatorio");
            }
            if (dto.disciplinaId() == null) {
                erros.add("O campo 'disciplinaId' é obrigatorio");
            }

            throw new CampoObrigatorioException(erros.toString());
        }

        AlunoEntity aluno = alunoService.getEntity(dto.alunoId());
        DisciplinaEntity disciplina = disciplinaService.getEntity(dto.disciplinaId());

        if (repository.existsByAluno_AlunoIdAndDisciplina_DisciplinaId(aluno.getAlunoId(), disciplina.getDisciplinaId())) {
            throw new MatriculaDuplicadaException("O aluno já está matriculado nesta disciplina.");
        }

        MatriculaEntity matricula = new MatriculaEntity(aluno, disciplina);
        matricula = repository.save(matricula);

        return new ResponseMatriculaDto(
                matricula.getMatriculaId(),
                matricula.getDisciplina().getDisciplinaId(),
                matricula.getDisciplina().getNome(),
                matricula.getAluno().getAlunoId(),
                matricula.getAluno().getNome()
        );
    }


    @Override
    public void delete(Long id) {
        getEntity(id);

        if (getEntity(id).getNotas().size() > 0) {
            throw new MatriculaComNotaCadastradaException("Matrícula não pode ser deletada, há nota(s) cadastrada(s)");
        }

        repository.deleteById(id);
    }

    @Override
    public MatriculaEntity update(Long id, MatriculaEntity dto) {
        getEntity(id);
        if (dto.getAluno() == null || dto.getDisciplina() == null) {
            throw new CampoObrigatorioException("Os campos 'alunoId' e 'disciplinaId' são obrigatório ao atualizar uma matrícula");
        }

//        Arepository.findById(dto.getAluno().getAlunoId()).orElseThrow(() -> new NotFoundException("Não encontrado aluno com este id"));
//        Drepository.findById(dto.getDisciplina().getDisciplinaId()).orElseThrow(() -> new NotFoundException("Não encontrada disciplina com este id"));

        if (repository.existsByAluno_AlunoIdAndDisciplina_DisciplinaId(dto.getAluno().getAlunoId(), dto.getDisciplina().getDisciplinaId())) {
            throw new MatriculaDuplicadaException("O aluno já está matriculado nesta disciplina.");
        }
        dto.setMatriculaId(id);
        return repository.saveAndFlush(dto);
    }

    @Override
    public MatriculaEntity getEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrada matrícula com id: " + id));
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
        List<MatriculaEntity> matriculas = repository.findAllByDisciplinaDisciplinaId(id);
        if (matriculas.isEmpty()) {
            throw new NotFoundException("Disciplina inexistente ou não associada a nunhuma matrícula");
        }
        return matriculas;
    }

    @Override
    public List<MatriculaEntity> getEntitiesAluno(Long id) {
        List<MatriculaEntity> matriculas = repository.findAllByAlunoAlunoId(id);
        if (matriculas.isEmpty()) {
            throw new NotFoundException("Aluno inexistente ou não matriculado a nenhuma disciplina");
        }
        return matriculas;
    }

    @Override
    public MediasAlunoDto getMediasAluno(Long id) {

        AlunoEntity aluno = alunoService.getEntity(id);

        if (aluno.getMatriculas().isEmpty()) {
            throw new NotFoundException("Aluno não está matriculado em nenhuma disciplina.");
        }


        List<MatriculaEntity> matriculas = getEntitiesAluno(id);
        double notas = 0.0;
        List<MediaMatriculaDto> mediaMaticula = new ArrayList<>();

        for (MatriculaEntity maticula : matriculas) {
            mediaMaticula.add(new MediaMatriculaDto(maticula.getDisciplina().getNome(), maticula.getMediaFinal()));
            notas += maticula.getMediaFinal();
        }
        return new MediasAlunoDto(mediaMaticula, (notas / matriculas.size()));
    }
}
