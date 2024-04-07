package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.RequestNotaDto;
import com.senai.projetonotas.dto.ResponseNotaDto;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.exception.customException.ProfessorNaoAssociadoException;
import com.senai.projetonotas.repository.NotaRepository;
import com.senai.projetonotas.service.MatriculaService;
import com.senai.projetonotas.service.NotaService;
import com.senai.projetonotas.service.ProfessorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
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
            (dto.nota() < 0  && dto.nota() > 10)
            || (dto.coeficiente() < 0 && dto.coeficiente() > 10)
            || dto.professorId() == null
            || dto.matriculaId() == null)  {
      ArrayList<String> erros = new ArrayList<>();

      if (dto.matriculaId() == null) {
        erros.add("O campo 'matriculaId' é obrigatorio, informe um valor valido");
      }
      if (dto.professorId() == null) {
        erros.add("O campo 'professorId' é obrigatorio, informe um valor valido");
      }

      if (dto.nota() == 0) {
        erros.add("O campo 'nota' é obrigatorio, informe um valor valido");
      }

      if (dto.coeficiente() == 0) {
        erros.add("O campo 'coeficiente' é obrigatorio, informe um valor valido");
      }

      throw new CampoObrigatorioException(erros.toString());
    }

    MatriculaEntity matricula = matriculaService.getEntity(dto.matriculaId());

    ProfessorEntity professor = professorService.getEntity(dto.professorId());


    if (matricula.getDisciplina().getProfessor().getProfessorId() != dto.professorId()) {
      throw new ProfessorNaoAssociadoException("O professor não está associado à matrícula fornecida.");
    }


    NotaEntity newNota = repository.saveAndFlush(new NotaEntity(dto.nota(),dto.coeficiente(),professor,matricula));
    matriculaService.updateMediaMatricula (newNota.getMatricula().getMatriculaId());
    return new ResponseNotaDto(newNota.getNotaId(), newNota.getNota(), newNota.getCoeficiente());
  }
  @Override
  public void delete(Long id) {
    NotaEntity newNota = getEntity(id);
    repository.deleteById(id);
    matriculaService.updateMediaMatricula (newNota.getMatricula().getMatriculaId());
  }

  @Override
  public List<NotaEntity> getNotasByMatriculaId(Long matriculaId) {
    List<NotaEntity> notas = repository.findAllByMatricula_MatriculaId(matriculaId);
    if (notas.isEmpty()) {
      throw new NotFoundException("Matrícula inexistente ou sem nota(s) cadastrada(s)");
    }
    return notas;
  }


  @Override
  public List<ResponseNotaDto> getNotasByMatriculaIdDto(Long matriculaId) {
    List<NotaEntity> notas = getNotasByMatriculaId(matriculaId);
    if (notas.isEmpty()) {
      throw new NotFoundException("Matrícula inexistente ou sem nota(s) cadastrada(s)");
    }
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
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Nota não encontrada com o id: " +id));
  }

  @Override
  public List<NotaEntity> getEntities() {
    return repository.findAll();
  }

  @Override
  public List<NotaEntity> getEntities(Long id) {
    return repository.findAll();
  }

}
