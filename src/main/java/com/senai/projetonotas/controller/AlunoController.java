package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.CreateAlunoDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.UpdateAlunoDto;
import com.senai.projetonotas.entity.AlunoEntity;
import com.senai.projetonotas.service.AlunoService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alunos")
@RequiredArgsConstructor
@Slf4j
public class AlunoController {

  private final AlunoService service;

  @GetMapping()
  public ResponseEntity<List<AlunoEntity>> getEntities() {
    log.info("GET/alunos -->Início");

    List<AlunoEntity> alunos = service.getEntities();
    log.info("GET /alunos -> Foram encontrados {} registros", alunos.size());

    log.info("GET /alunos -> 200 OK");
    log.debug("GET /alunos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(alunos));

    return ResponseEntity.ok(service.getEntities());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseAlunoDto> getEntity(@PathVariable(name = "id") Long id) {
    log.info("GET/alunos/{} -->Início",id);

    ResponseAlunoDto dto = service.getEntityDto(id);
    log.info("GET /alunos/{} -> ID encontrado", id);

    log.info("GET /alunos/{} -> 200 OK",id);
    log.debug("GET /alunos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));

    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<ResponseAlunoDto> create(@RequestBody CreateAlunoDto dto) {
    log.info("POST /alunos");

    ResponseAlunoDto NovoAlunodto = service.create(dto);
    log.info("POST /alunos -> Cadastrado");

    log.info("POST /alunos -> 201 CREATED");
    log.debug("POST /alunos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(dto.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(NovoAlunodto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseAlunoDto> update(@PathVariable(name = "id") Long id, @RequestBody UpdateAlunoDto dto) {
    log.info("PUT /alunos/{}", id);

    ResponseAlunoDto aluno = service.update(id, dto);
    log.info("PUT /alunos/{} -> Atualizado", id);

    log.info("PUT /alunos/{} -> 200 OK", id);
    log.debug("PUT /alunos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(aluno.toString()));
    return ResponseEntity.ok(service.update(id,dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    log.info("DELETE /alunos/{}", id);

    service.delete(id);
    log.info("DELETE /alunos/{} -> Excluído", id);

    log.info("DELETE /alunos/{} -> 204 NO CONTENT", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
