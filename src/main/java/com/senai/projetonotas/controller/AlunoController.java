package com.senai.projetonotas.controller;

import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alunos")
@RequiredArgsConstructor
public class AlunoController {

  private final AlunoService service;

  @GetMapping()
  public ResponseEntity<List<AlunoEntity>> getEntities() {
    return ResponseEntity.ok(service.getEntities());
  }

  @GetMapping("{id}")
  public ResponseEntity<AlunoEntity> getEntity(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(service.getEntity(id));
  }

  @PostMapping
  public ResponseEntity<AlunoEntity> create(@RequestBody AlunoEntity dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
  }

  @PutMapping("{id}")
  public ResponseEntity<AlunoEntity> update(@PathVariable(name = "id") Long id, @RequestBody AlunoEntity dto) {
    return ResponseEntity.ok(service.update(id,dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
