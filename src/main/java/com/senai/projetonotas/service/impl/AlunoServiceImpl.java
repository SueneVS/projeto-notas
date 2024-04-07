package com.senai.projetonotas.service.impl;

import ch.qos.logback.core.joran.spi.ElementPath;
import com.senai.projetonotas.dto.CreateAlunoDto;
import com.senai.projetonotas.dto.ResponseNovoAlunoDto;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.AlunoRepository;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository repository;
    @Override
    public ResponseNovoAlunoDto create(CreateAlunoDto dto) {
        AlunoEntity aluno = new AlunoEntity(dto.nome(), dto.dataNascimento());

        log.info("Criando aluno -> Salvar: \n{}\n", JsonUtil.objetoParaJson(dto));
        aluno = repository.save(aluno);

        log.info("Criando aluno-> Salvo com sucesso");
        log.debug("Criando aluno -> Registro Salvo: \n{}\n", JsonUtil.objetoParaJson(aluno));

        return  new ResponseNovoAlunoDto(aluno.getAlunoId(),aluno.getNome(),aluno.getDataNascimento());
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        log.info("Excluindo aluno com id ({}) -> Excluindo", id);
        repository.deleteById(id);
        log.info("Excluindo aluno com id ({}) -> Excluído com sucesso", id);
    }

    @Override
    public AlunoEntity update(Long id, AlunoEntity dto) {
        getEntity(id);
        log.info("Alterando aluno com id ({}) -> Salvar: \n{}\n", id, JsonUtil.objetoParaJson(dto));
        if (dto.getNome() == null || dto.getDataNascimento() == null ) {
            throw new CampoObrigatorioException("Os campos 'nome' e 'dataNascimento' são obrigatórios ao atualizar um aluno");
        }
        dto.setAlunoId(id);

       AlunoEntity aluno = repository.save(dto);
        log.info("Alterando aluno -> Salvo com sucesso");
        log.debug("Alterando aluno -> Registro Salvo: \n{}\n", JsonUtil.objetoParaJson(aluno));
        return repository.saveAndFlush(dto);
    }

    @Override
    public AlunoEntity getEntity(Long id) {
        log.info("Buscando aluno por id ({})", id);

        Optional<AlunoEntity> aluno = repository.findById(id);

        log.info("Buscando aluno por id ({}) -> Encontrado", id);
        log.debug("Buscando aluno por id ({}) -> Registro encontrado:\n{}\n", id, JsonUtil.objetoParaJson(aluno));

        return repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrado aluno com id: " +id));
    }

    @Override
    public List<AlunoEntity> getEntities() {
        log.info("Buscando todos os alunos");

        List<AlunoEntity> alunos = repository.findAll();

        log.info("Buscando todos os alunos -> {} Encontrados", alunos.size());
        log.debug("Buscando todos os alunos -> Registros encontrados:\n{}\n", JsonUtil.objetoParaJson(alunos));

        return repository.findAll();

    }

    @Override
    public List<AlunoEntity> getEntities(Long id) {
        return repository.findAll();
    }
}
