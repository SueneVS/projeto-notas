package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.DisciplinaService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("disciplinas")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(ColecaoService colecaoService) {
        this.service = colecaoService.getDisciplinaService();
        this.service.setProfessorService(colecaoService.getProfessorService());
    }

    @PostMapping
    public ResponseEntity<ResponseDisciplinaDto> create(@RequestBody RequestDisciplinaDto dto) {

        log.info("POST /disciplinas");

        ResponseDisciplinaDto NovaDisciplina = service.create(dto);
        log.info("POST /disciplinas -> Cadastrada");

        log.info("POST /disciplinas -> 201 CREATED");
        log.debug("POST /disciplinas-> Response Body:\n{}\n", JsonUtil.objetoParaJson(dto.toString()));

        return ResponseEntity.status(HttpStatus.CREATED).body(NovaDisciplina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        log.info("DELETE /disciplinas/{}", id);

        service.delete(id);
        log.info("DELETE /disciplinas/{} -> Excluída", id);

        log.info("DELETE /disciplinas/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDisciplinaDto> update(@PathVariable(name = "id") Long id, @RequestBody RequestDisciplinaDto dto) {
        log.info("PUT /disciplinas/{}", id);

        ResponseDisciplinaDto disciplina = service.update(id, dto);
        log.info("PUT /disciplinas/{} -> Atualizado", id);

        log.info("PUT /disciplinas/{} -> 200 OK", id);
        log.debug("PUT /disciplinas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(disciplina.toString()));
        return ResponseEntity.ok(disciplina);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseDisciplinaDto>> getEntities() {
        log.info("GET/disciplinas -->Início");

        List<ResponseDisciplinaDto> disciplinas = service.getEntitiesDto();
        log.info("GET /disciplinas -> Foram encontrados {} registros", disciplinas.size());

        log.info("GET /disciplinas -> 200 OK");
        log.debug("GET /disciplinas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(disciplinas.toString()));
        return ResponseEntity.ok(disciplinas);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseDisciplinaDto> getEntity(@PathVariable(name = "id") Long id) {

        log.info("GET/disciplinas/{} -->Início",id);

        ResponseDisciplinaDto dto = service.getEntityDto(id);
        log.info("GET /disciplinas/{} -> ID encontrado", id);

        log.info("GET /disciplinas/{} -> 200 OK",id);
        log.debug("GET /disciplinas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<List<ResponseDisciplinaDto>> getEntitiesProfessor(@PathVariable(name = "id")Long id) {
        log.info("GET/disciplina/professores/{} -->Início",id);

        List<ResponseDisciplinaDto> dto = service.getEntitiesProfessordto(id);

        log.info("GET /disciplinas/professores/{} -> Encontrado", id);

        log.info("GET /disciplinas/professores/{} -> 200 OK",id);

        return ResponseEntity.ok(dto);
    }


}
