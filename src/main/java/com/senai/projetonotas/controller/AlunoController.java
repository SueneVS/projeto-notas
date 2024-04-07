package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.CreateAlunoDto;
import com.senai.projetonotas.dto.ResponseNovoAlunoDto;
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

  @GetMapping("{id}")
  public ResponseEntity<AlunoEntity> getEntity(@PathVariable(name = "id") Long id) {
    log.info("GET/alunos/{} -->Início",id);

    List<AlunoEntity> aluno = service.getEntities(id);
    log.info("GET /alunos/{} -> ID encontrado", id);

    log.info("GET /alunos/{} -> 200 OK",id);
    log.debug("GET /alunos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(aluno));

    return ResponseEntity.ok(service.getEntity(id));
  }

  @PostMapping
  public ResponseEntity<ResponseNovoAlunoDto> create(@RequestBody CreateAlunoDto dto) {
    log.info("POST /alunos");

    ResponseNovoAlunoDto NovoAlunodto = service.create(dto);
    log.info("POST /alunos -> Cadastrado");

    log.info("POST /alunos -> 201 CREATED");
    log.debug("POST /alunos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(dto));

    return ResponseEntity.status(HttpStatus.CREATED).body(NovoAlunodto);
  }

  @PutMapping("{id}")
  public ResponseEntity<AlunoEntity> update(@PathVariable(name = "id") Long id, @RequestBody AlunoEntity dto) {
    log.info("PUT /alunos/{}", id);

    AlunoEntity aluno = service.update(id, dto);
    log.info("PUT /alunos/{} -> Atualizado", id);

    log.info("PUT /alunos/{} -> 200 OK", id);
    log.debug("PUT /alunos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(aluno));
    return ResponseEntity.ok(service.update(id,dto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
    log.info("DELETE /alunos/{}", id);

    service.delete(id);
    log.info("DELETE /alunos/{} -> Excluído", id);

    log.info("DELETE /alunos/{} -> 204 NO CONTENT", id);
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
