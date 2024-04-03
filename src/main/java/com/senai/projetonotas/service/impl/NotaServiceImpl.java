package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.repository.NotaRepository;
import com.senai.projetonotas.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

  private final NotaRepository repository;
  @Override
  public NotaEntity create(NotaEntity dto) {
    return repository.save(dto);
  }

  @Override
  public void delete(Long id) {
    getEntity(id);
    repository.deleteById(id);
  }

  @Override
  public NotaEntity update(Long id, NotaEntity dto) {
    getEntity(id);
    dto.setNotaId(id);
    return repository.saveAndFlush(dto);
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
}
