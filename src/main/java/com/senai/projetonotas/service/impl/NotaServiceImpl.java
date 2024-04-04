package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.repository.MatriculaRepository;
import com.senai.projetonotas.repository.NotaRepository;
import com.senai.projetonotas.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

  private final NotaRepository repository;
  private final MatriculaRepository Mrepository;
  @Override
  public NotaEntity create(NotaEntity dto) {
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
  public NotaEntity update(Long id, NotaEntity dto) {
    getEntity(id);
    dto.setNotaId(id);
    if(dto.getNota() > 10){
      throw new RuntimeException("Nota do aluno supoerior a 10");
    }
    NotaEntity newNota = repository.saveAndFlush(dto);
    calculaMediaDisciplina(newNota);
    return getEntity(id);
  }

  @Override
  public NotaEntity getEntity(Long id) {
    return repository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
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
