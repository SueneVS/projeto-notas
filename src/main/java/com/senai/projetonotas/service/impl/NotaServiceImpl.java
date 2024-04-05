package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.exception.customException.CampoObrigatorioException;
import com.senai.projetonotas.exception.customException.NotFoundException;
import com.senai.projetonotas.exception.customException.ProfessorNaoAssociadoException;
import com.senai.projetonotas.repository.MatriculaRepository;
import com.senai.projetonotas.repository.NotaRepository;
import com.senai.projetonotas.repository.ProfessorRepository;
import com.senai.projetonotas.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

  private final NotaRepository repository;
  private final MatriculaRepository Mrepository;
  private final ProfessorRepository Prepository;
  @Override
  public NotaEntity create(NotaEntity dto) {

    if (dto.getNota() == null || dto.getCoeficiente() == null)  {
      throw new CampoObrigatorioException("Os campos 'nota' e 'coeficiente' são obrigatórios para criar uma nota");
    }

    MatriculaEntity matricula = Mrepository.findById(dto.getMatricula().getMatriculaId())
            .orElseThrow(() -> new NotFoundException("Não encontrada matrícula com este id"));

    ProfessorEntity professor = Prepository.findById(dto.getProfessor().getProfessorId())
            .orElseThrow(() -> new NotFoundException("Professor não encontrado com este id"));

    if (matricula.getDisciplina().getProfessor().getProfessorId() != dto.getProfessor().getProfessorId()) {
      throw new ProfessorNaoAssociadoException("O professor não está associado à matrícula fornecida.");
    }

    if(dto.getNota() > 10.0){
      throw new RuntimeException("Nota do aluno superior a 10");
    }
    NotaEntity newNota = repository.saveAndFlush(dto);
    calculaMediaDisciplina(newNota);
    return getEntity(newNota.getNotaId());
  }
  @Override
  public void delete(Long id) {
    NotaEntity newNota = getEntity(id);
    repository.deleteById(id);
    calculaMediaDisciplina(newNota);
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
  public NotaEntity update(Long id, NotaEntity dto) {
    getEntity(id);
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

  private void calculaMediaDisciplina(NotaEntity newNota){
    MatriculaEntity matricula = Mrepository.findById(newNota.getMatricula().getMatriculaId()).orElseThrow(() -> new RuntimeException("Error"));

    double coeficiente = 0.0;
    double somaNotas = 0.0;

    List<NotaEntity> notas = matricula.getNotas();

    for(NotaEntity nota: notas){
      coeficiente += nota.getCoeficiente();
      somaNotas += nota.getNota() *nota.getCoeficiente();
    }

    if(coeficiente > 1.0){
      throw new RuntimeException("Coeficiente maior que 1");
    }

    matricula.setMediaFinal((somaNotas));
    Mrepository.saveAndFlush(matricula);
  }

}
