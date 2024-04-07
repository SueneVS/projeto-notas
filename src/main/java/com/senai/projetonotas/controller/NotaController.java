package com.senai.projetonotas.controller;

import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.NotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notas")
public class NotaController {

  private final NotaService service;

  public NotaController(ColecaoService colecaoService) {
    this.service = colecaoService.getNotaService();
    this.service.setMatriculaService(colecaoService.getMatriculaService());
    this.service.setProfessorService(colecaoService.getProfessorService());

  }

  // Não há esses metodos de Get em Notas
  /*
  @GetMapping()
  public ResponseEntity<List<NotaEntity>> getEntities() {
    return ResponseEntity.ok(service.getEntities());
  }

  @GetMapping("{id}")
  public ResponseEntity<NotaEntity> getEntity(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(service.getEntity(id));
  }

  */
  @GetMapping("/matriculas/{id}")
  public ResponseEntity<List<NotaEntity>> getNotasByMatriculaId(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getNotasByMatriculaId(id));
  }

  @PostMapping
  public ResponseEntity<NotaEntity> create(@RequestBody NotaEntity dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
  }

  @PutMapping("{id}")
  public ResponseEntity<NotaEntity> update(@PathVariable(name = "id") Long id, @RequestBody NotaEntity dto) {
    return ResponseEntity.ok(service.update(id,dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
