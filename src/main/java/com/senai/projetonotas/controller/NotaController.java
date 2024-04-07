package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.RequestNotaDto;
import com.senai.projetonotas.dto.ResponseNotaDto;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.NotaService;
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

  @GetMapping("/matriculas/{id}")
  public ResponseEntity<List<ResponseNotaDto>> getNotasByMatriculaId(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getNotasByMatriculaIdDto(id));
  }

  @PostMapping
  public ResponseEntity<ResponseNotaDto> create(@RequestBody RequestNotaDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
