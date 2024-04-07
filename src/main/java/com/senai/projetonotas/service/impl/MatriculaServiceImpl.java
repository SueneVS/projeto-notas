package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.*;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.MatriculaComNotaCadastradaException;
import com.senai.projetonotas.exception.customException.MatriculaDuplicadaException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.MatriculaRepository;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.service.MatriculaService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository repository;
    private DisciplinaService disciplinaService;
    private AlunoService alunoService;

    public MatriculaServiceImpl(MatriculaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setDisciplinaService(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @Override
    public void setAlunoService(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

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
        log.info("Buscando aluno por id ({}) -> Encontrado", dto.alunoId());
        AlunoEntity aluno = alunoService.getEntity(dto.alunoId());
        log.info("Buscando disciplina por id ({}) -> Encontrado", dto.disciplinaId());
        DisciplinaEntity disciplina = disciplinaService.getEntity(dto.disciplinaId());

        log.info("Valida se o aluna ja esta cadastrado na disciplina");
        if (repository.existsByAluno_AlunoIdAndDisciplina_DisciplinaId(aluno.getAlunoId(), disciplina.getDisciplinaId())) {
            throw new MatriculaDuplicadaException("O aluno já está matriculado nesta disciplina.");
        }

        log.info("transforma DTO em entidade");
        MatriculaEntity matricula = new MatriculaEntity(aluno, disciplina);

        log.info("Criando matricula-> Salvo com sucesso");
        matricula = repository.save(matricula);

        log.info("transformando a matricula em DTO");
        return new ResponseMatriculaDto(
                matricula.getMatriculaId(),
                matricula.getMediaFinal(),
                matricula.getDisciplina().getDisciplinaId(),
                matricula.getDisciplina().getNome(),
                matricula.getAluno().getAlunoId(),
                matricula.getAluno().getNome()
        );
    }


    @Override
    public void delete(Long id) {
        MatriculaEntity matricula = getEntity(id);

        log.info("Valida se matricula ja tem nota lançada");
        if (!matricula.getNotas().isEmpty()) {
            throw new MatriculaComNotaCadastradaException("Matrícula não pode ser deletada, há nota(s) cadastrada(s)");
        }
        log.info("Excluindo matricula com id ({}) -> Excluindo", id);
        repository.delete(matricula);
        log.info("Excluindo matricula com id ({}) -> Excluído com sucesso", id);

    }



    @Override
    public MatriculaEntity getEntity(Long id) {
        log.info("Buscando matricula por id ({})", id);

        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrada matrícula com id: " + id));
    }

    @Override
    public ResponseMatriculaDto getEntityDto(Long id) {
        MatriculaEntity matricula = getEntity(id);

        log.info("Buscando matricula por id ({}) -> Encontrado", id);
        log.debug("Buscando matricula por id ({}) -> Registro encontrado:\n{}\n", id, JsonUtil.objetoParaJson(matricula.toString()));

        log.info("transformando a disciplinas em DTO");
        return new ResponseMatriculaDto(matricula.getMatriculaId(),
                matricula.getMediaFinal(),
                matricula.getDisciplina().getDisciplinaId(),
                matricula.getDisciplina().getNome(),
                matricula.getAluno().getAlunoId(),
                matricula.getAluno().getNome()
        );

    }

    @Override
    public List<MatriculaEntity> getEntities() {
        log.info("Buscando todos as matriculas");
        return repository.findAll();
    }

    @Override
    public List<ResponseMatriculaDto> getEntitiesDtos() {
        List<MatriculaEntity> matriculas = getEntities();

        log.info("transformando a matriculas em DTO");
        return matriculas.stream()
            .map(matricula -> new ResponseMatriculaDto(
                matricula.getMatriculaId(),
                matricula.getMediaFinal(),
                matricula.getDisciplina().getDisciplinaId(),
                matricula.getDisciplina().getNome(),
                matricula.getAluno().getAlunoId(),
                matricula.getAluno().getNome()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public List<MatriculaEntity> getEntitiesDisciplina(Long id) {
        List<MatriculaEntity> matriculas = repository.findAllByDisciplinaDisciplinaId(id);

        log.info("Valida se a disciplina é existente ou se apresenta alguma matricula vinculada");
        if (matriculas.isEmpty()) {
            throw new NotFoundException("Disciplina inexistente ou não associada a nunhuma matrícula");
        }
        return matriculas;
    }

    @Override
    public List<ResponseMatriculaDto> getEntitiesDisciplinaDto(Long id) {
        List<MatriculaEntity> matriculas = getEntitiesDisciplina(id);

        log.info("transformando a matriculas em DTO");
        return matriculas.stream()
                .map(matricula -> new ResponseMatriculaDto(
                        matricula.getMatriculaId(),
                        matricula.getMediaFinal(),
                        matricula.getDisciplina().getDisciplinaId(),
                        matricula.getDisciplina().getNome(),
                        matricula.getAluno().getAlunoId(),
                        matricula.getAluno().getNome()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<MatriculaEntity> getEntitiesAluno(Long id) {
        log.info("Buscando matriculas do aluno com id ({}) -> Encontrado", id);

        List<MatriculaEntity> matriculas = repository.findAllByAlunoAlunoId(id);
        log.debug("Buscando matriculas do aluno com id ({}) -> Registro encontrado:\n{}\n", id, JsonUtil.objetoParaJson(matriculas.toString()));

        log.info("Valida se o aluna é existente ou se apresenta alguma matricula vinculada");
        if (matriculas.isEmpty()) {
            throw new NotFoundException("Aluno inexistente ou não matriculado a nenhuma disciplina");
        }
        return matriculas;
    }

    @Override
    public List<ResponseMatriculaDto> getEntitiesAlunoDto(Long id) {
        List<MatriculaEntity> matriculas = getEntitiesAluno(id);

        log.info("transformando a matriculas em DTO");
        return matriculas.stream()
                .map(matricula -> new ResponseMatriculaDto(
                        matricula.getMatriculaId(),
                        matricula.getMediaFinal(),
                        matricula.getDisciplina().getDisciplinaId(),
                        matricula.getDisciplina().getNome(),
                        matricula.getAluno().getAlunoId(),
                        matricula.getAluno().getNome()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public MediaAlunoDto getMediaAluno(Long id) {

        AlunoEntity aluno = alunoService.getEntity(id);

        log.info("Valida se o aluno é existente ou se apresenta alguma matricula vinculada");
        if (aluno.getMatriculas().isEmpty()) {
            throw new NotFoundException("Aluno não está matriculado em nenhuma disciplina.");
        }

        List<MatriculaEntity> matriculas = getEntitiesAluno(id);
        double notas = 0.0;

        log.info("valcula a media ");
        for (MatriculaEntity maticula : matriculas) {
            notas += maticula.getMediaFinal()/matriculas.size();
        }
        log.info("Cria DTO da media do aluno");
        return new MediaAlunoDto(notas);
    }


    @Override
    public void updateMediaMatricula(Long id) {
        MatriculaEntity matricula = getEntity(id);
        double coeficiente = 0.0;
        double somaNotas = 0.0;

        log.info("Pega todos todas as notas da matricula");
        List<NotaEntity> notas = matricula.getNotas();

        log.info("Realiza a soma do coeficiente e a soma da medica com o seu coeficiente");
        for(NotaEntity nota: notas){
            coeficiente += nota.getCoeficiente();
            somaNotas += nota.getNota() *nota.getCoeficiente();
        }

        log.info("Valida se a soma do coeficiente é valido");
        if(coeficiente > 1.0 || coeficiente < 0.0){
            throw new RuntimeException("Coeficiente deve estar em 0.0 e 1.0");
        }
        log.info("atribuiu o valor calculado na media final ");
        matricula.setMediaFinal((somaNotas));

        repository.saveAndFlush(matricula);
        log.info("Alterando a media da matricula -> Salvo com sucesso");
    }
}
