package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.RequestNotaDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.ResponseMatriculaDto;
import com.senai.projetonotas.dto.ResponseNotaDto;
import com.senai.projetonotas.entity.NotaEntity;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.NotaService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
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
    log.info("GET/notas/{} -->Início",id);

    List<ResponseNotaDto> notas = service.getNotasByMatriculaIdDto(id);
    log.info("GET /notas/{} -> ID encontrado", id);

    log.info("GET /notas/{} -> 200 OK",id);
    log.debug("GET /notas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(notas.toString()));
    return ResponseEntity.status(HttpStatus.OK).body(notas);
  }

  @PostMapping
  public ResponseEntity<ResponseNotaDto> create(@RequestBody RequestNotaDto dto) {

    log.info("POST /notas");

    ResponseNotaDto NovaNotadto = service.create(dto);
    log.info("POST /notas -> Cadastrado");

    log.info("POST /notas -> 201 CREATED");
    log.debug("POST /notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(dto.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(NovaNotadto);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    log.info("DELETE /notas/{}", id);

    service.delete(id);
    log.info("DELETE /notas/{} -> Excluído", id);

    log.info("DELETE /notas/{} -> 204 NO CONTENT", id);

    return ResponseEntity.noContent().build();
  }
}
