package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.*;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.MatriculaService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("matriculas")
public class MatriculaController {


    private final MatriculaService service;

    public MatriculaController(ColecaoService colecaoService) {
        this.service = colecaoService.getMatriculaService();
        this.service.setAlunoService(colecaoService.getAlunoService());
        this.service.setDisciplinaService(colecaoService.getDisciplinaService());
    }

    @PostMapping
    public ResponseEntity<ResponseMatriculaDto> create(@RequestBody RequestMatriculaDto dto) {

        log.info("POST /matriculas");

        ResponseMatriculaDto NovaMatricula = service.create(dto);
        log.info("POST /matriculas -> Cadastrado");

        log.info("POST /matriculas -> 201 CREATED");
        log.debug("POST /matriculas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(dto.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(NovaMatricula);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id")Long id) {

        log.info("DELETE /matriculas/{}", id);

        service.delete(id);
        log.info("DELETE /matriculas/{} -> Excluído", id);

        log.info("DELETE /matriculas/{} -> 204 NO CONTENT", id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseMatriculaDto> getEntity(@PathVariable(name = "id") Long id) {
        log.info("GET/matriculas/{} -->Início", id);
        List<ResponseMatriculaDto> dto = service.getEntitiesDtos();
        log.info("GET /matriculas/{} -> Matricula encontrada", id);
        log.debug("GET /matriculas{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));
        return ResponseEntity.ok(service.getEntityDto(id));
    }


    @GetMapping()
    public ResponseEntity<List<ResponseMatriculaDto>> getEntities() {
        log.info("GET/matriculas -->Início");

        List<ResponseMatriculaDto> matriculas = service.getEntitiesDtos();
        log.info("GET /matriculas -> Foram encontrados {} registros", matriculas.size());

        log.info("GET /matriculas-> 200 OK");
        log.debug("GET /matriculas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(matriculas.toString()));
        return ResponseEntity.ok(matriculas);
    }


    @GetMapping("/alunos/{id}")
    public ResponseEntity<List<ResponseMatriculaDto>> getEntitiesAluno(@PathVariable(name = "id")Long id) {
        log.info("GET/matriculas/alunos/{} -->Início", id);
        List<ResponseMatriculaDto> dto = service.getEntitiesAlunoDto(id);
        log.info("GET /matriculas/alunos/{} -> Matricula por aluno encontrado", id);
        log.debug("GET /matriculas/alunos{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<List<ResponseMatriculaDto>> getEntitiesDisciplina(@PathVariable(name = "id")Long id) {
        log.info("GET/matriculas/disciplinas/{} -->Início", id);
        List<ResponseMatriculaDto> dto = service.getEntitiesDisciplinaDto(id);
        log.info("GET /matriculas/disciplina/{} -> Matricula por disciplina encontrada", id);
        log.debug("GET /matriculas/disciplina{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/alunos/{id}/media")
    public ResponseEntity<MediaAlunoDto> getMediasAluno(@PathVariable(name = "id")Long id) {

        log.info("GET/matriculas/alunos/{}/media -->Início", id);
        List<ResponseMatriculaDto> dto = service.MediaAlunoDto(id);
        log.info("GET /matriculas/alunos/{}/media-> Matricula por disciplina encontrada", id);
        log.debug("GET /matriculas/alunos/{}/media -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));
        return ResponseEntity.ok(service.getMediaAluno(id));
    }

}
