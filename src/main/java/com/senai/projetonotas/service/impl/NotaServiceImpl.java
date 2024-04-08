package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.RequestNotaDto;
import com.senai.projetonotas.dto.ResponseNotaDto;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.CoeficienteAcimaDoLimiteException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.exception.customException.ProfessorNaoAssociadoException;
import com.senai.projetonotas.repository.NotaRepository;
import com.senai.projetonotas.service.MatriculaService;
import com.senai.projetonotas.service.NotaService;
import com.senai.projetonotas.service.ProfessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class NotaServiceImpl implements NotaService {

  private final NotaRepository repository;
  private  MatriculaService matriculaService;
  private  ProfessorService professorService;

  public NotaServiceImpl(NotaRepository repository) {
    this.repository = repository;


  }

  @Override
  public void setMatriculaService(MatriculaService matriculaService) {
    this.matriculaService = matriculaService;
  }

  @Override
  public void setProfessorService(ProfessorService professorService) {
    this.professorService = professorService;
  }

  @Override
  public ResponseNotaDto create(RequestNotaDto dto) {

    if (
            (dto.nota() < 0.0  || dto.nota() > 10.0)
            || (dto.coeficiente() <= 0.0 || dto.coeficiente() > 1.0)
            || dto.matriculaId() == null)  {
      ArrayList<String> erros = new ArrayList<>();

      if (dto.matriculaId() == null) {
        erros.add("O campo 'matriculaId' é obrigatorio, informe um valor valido");
      }

      if (dto.nota() < 0.0  || dto.nota() > 10.0) {
        erros.add("O campo 'nota' é obrigatorio, informe um valor valido");
      }

      if (dto.coeficiente() <= 0.0 || dto.coeficiente() > 1.0) {
        erros.add("O campo 'coeficiente' é obrigatorio, informe um valor valido");
      }

      throw new CampoObrigatorioException(erros.toString());
    }

    log.info("Buscando matricula por id ({}) -> Encontrado", dto.matriculaId());
    MatriculaEntity matricula = matriculaService.getEntity(dto.matriculaId());


    log.info("Valida se o coeficiente é valido");
    if ((matricula.somaCoeficiente() + dto.coeficiente()) > 1.0) {
      throw new CoeficienteAcimaDoLimiteException("A Soma do Coeficiente de "+(matricula.somaCoeficiente() + dto.coeficiente())+" passou se do limite permitido .");
    }

    log.info("Criando nota-> Salvo com sucesso");
    NotaEntity newNota = repository.saveAndFlush(
            new NotaEntity(dto.nota(),
                    dto.coeficiente(),
                    matricula.getDisciplina().getProfessor(),
                    matricula));
    matriculaService.updateMediaMatricula (matricula.getMatriculaId());

    log.info("transformando a nota em DTO");
    return new ResponseNotaDto(newNota.getNotaId(), newNota.getNota(), newNota.getCoeficiente());
  }
  @Override
  public void delete(Long id) {
    NotaEntity newNota = getEntity(id);

    log.info("Excluindo nota com id ({}) -> Excluindo", id);
    repository.deleteById(id);
    matriculaService.updateMediaMatricula (newNota.getMatricula().getMatriculaId());
  }

  @Override
  public List<NotaEntity> getNotasByMatriculaId(Long matriculaId) {
    log.info("Buscando notas pela matricula por id ({}) -> Encontrado", matriculaId);
    List<NotaEntity> notas = repository.findAllByMatricula_MatriculaId(matriculaId);

    log.info("Valida se a matricula tem nota vinculado");
    if (notas.isEmpty()) {
      throw new NotFoundException("Matrícula inexistente ou sem nota(s) cadastrada(s)");
    }

    log.info("Retorna todas as notas da matricula");
    return notas;
  }


  @Override
  public List<ResponseNotaDto> getNotasByMatriculaIdDto(Long matriculaId) {
    List<NotaEntity> notas = getNotasByMatriculaId(matriculaId);
    if (notas.isEmpty()) {
      throw new NotFoundException("Matrícula inexistente ou sem nota(s) cadastrada(s)");
    }
    log.info("transformando as notas em DTO");
    return notas.stream()
            .map(nota -> new ResponseNotaDto(nota.getNotaId(), nota.getNota(), nota.getCoeficiente()))
            .collect(Collectors.toList());
  }

  @Override
  public NotaEntity update(Long id, NotaEntity dto) {
    throw new UnsupportedOperationException("Não é permitido fazer alterações em notas já lançadas. Delete a nota, caso necessário.");
  }

  @Override
  public NotaEntity getEntity(Long id) {
    log.info("Buscando nota por id ({})", id);
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Nota não encontrada com o id: " +id));
  }

  @Override
  public List<NotaEntity> getEntities() {
    log.info("Buscando todos as notas");
    return repository.findAll();
  }


}
