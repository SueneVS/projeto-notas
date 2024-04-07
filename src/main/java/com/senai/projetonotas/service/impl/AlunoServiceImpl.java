package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.CreateAlunoDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.UpdateAlunoDto;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.repository.AlunoRepository;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository repository;

    public AlunoServiceImpl(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseAlunoDto create(CreateAlunoDto dto) {

        if (dto.nome() == null || dto.dataNascimento() == null ) {
            ArrayList<String> erros = new ArrayList<>();

            if(dto.nome() == null){
                erros.add("O campo 'nome' é obrigatorio");
            }
            if(dto.dataNascimento() == null){
                erros.add("O campo 'dataNascimento' é obrigatorio");
            }

            throw new CampoObrigatorioException(erros.toString() );
        }

        AlunoEntity aluno = new AlunoEntity(dto.nome(), dto.dataNascimento());
        log.info("Criando aluno -> Salvar: \n{}\n", JsonUtil.objetoParaJson(dto.toString()));
        aluno = repository.save(aluno);

        log.info("Criando aluno-> Salvo com sucesso");

        return  new ResponseAlunoDto(aluno.getAlunoId(),aluno.getNome(),aluno.getDataNascimento());
    }

    @Override
    public void delete(Long id) {
        getEntity(id);
        log.info("Excluindo aluno com id ({}) -> Excluindo", id);
        repository.deleteById(id);
        log.info("Excluindo aluno com id ({}) -> Excluído com sucesso", id);
    }

    @Override
    public ResponseAlunoDto update(Long id, UpdateAlunoDto dto) {
        AlunoEntity aluno =  getEntity(id);
        log.info("Alterando aluno com id ({}) -> Salvar: \n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));

        if (dto.nome() != null && aluno.getNome() != dto.nome()) {
            aluno.setNome(dto.nome());
        }

        if (dto.dataNascimento() != null && aluno.getDataNascimento().isEqual(LocalDate.parse(dto.dataNascimento()))  ) {
            aluno.setDataNascimento(LocalDate.parse(dto.dataNascimento()));
        }
        aluno.setAlunoId(id);

        repository.save(aluno);
        log.info("Alterando aluno -> Salvo com sucesso");

        return new ResponseAlunoDto(aluno.getAlunoId(),aluno.getNome(), aluno.getDataNascimento());
    }

    @Override
    public ResponseAlunoDto getEntityDto(Long id) {
        log.info("Buscando aluno por id ({})", id);

        AlunoEntity aluno = repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrado aluno com id: " +id));

        log.info("Buscando aluno por id ({}) -> Encontrado", id);

        return new ResponseAlunoDto(aluno.getAlunoId(),aluno.getNome(),aluno.getDataNascimento());
    }

    @Override
    public AlunoEntity getEntity(Long id) {
        log.info("Buscando aluno por id ({})", id);

        AlunoEntity aluno = repository.findById(id).orElseThrow(() -> new NotFoundException("Não encontrado aluno com id: " +id));

        log.info("Buscando aluno por id ({}) -> Encontrado", id);

        return aluno;
    }

    @Override
    public List<AlunoEntity> getEntities() {
        log.info("Buscando todos os alunos");

        List<AlunoEntity> alunos = repository.findAll();

        log.info("Buscando todos os alunos -> {} Encontrados", alunos.size());

        return alunos;

    }


    @Override
    public List<ResponseAlunoDto> getEntitiesDtos() {
        log.info("Buscando todos os alunos");

        List<AlunoEntity> alunos = getEntities();

        log.info("Transformando os alunos em dto");

        return alunos.stream()
                .map(aluno -> new ResponseAlunoDto(
                        aluno.getAlunoId(),
                        aluno.getNome(),
                        aluno.getDataNascimento()
                        )
                )
                .collect(Collectors.toList());

    }


}
