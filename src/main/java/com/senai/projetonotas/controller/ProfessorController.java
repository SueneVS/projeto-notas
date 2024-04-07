package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.RequestProfessorDto;
import com.senai.projetonotas.dto.ResponseAlunoDto;
import com.senai.projetonotas.dto.ResponseProfessorDto;
import com.senai.projetonotas.dto.UpdateAlunoDto;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.ProfessorService;
import com.senai.projetonotas.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ColecaoService colecaoService) {
        this.service = colecaoService.getProfessorService();
    }

    @GetMapping
    public ResponseEntity<List<ResponseProfessorDto>> getEntities(){
        log.info("GET/professores -->Início");

        List<ResponseProfessorDto> professores = service.getEntitiesDto();
        log.info("GET /professores -> Foram encontrados {} registros", professores.size());

        log.info("GET /professores -> 200 OK");
        log.debug("GET /professores -> Response Body:\n{}\n", JsonUtil.objetoParaJson(professores.toString()));
        return ResponseEntity.status(HttpStatus.OK).body(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProfessorDto> getEntity(@PathVariable Long id){

        log.info("GET/professores/{} -->Início",id);

        ResponseProfessorDto dto = service.getEntityDto(id);
        log.info("GET /professores/{} -> ID encontrado", id);

        log.info("GET /professores/{} -> 200 OK",id);
        log.debug("GET /professores/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(dto.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping
    public ResponseEntity<ResponseProfessorDto> create(@RequestBody RequestProfessorDto professor){

        log.info("POST /professores");

        ResponseProfessorDto NovoProfessordto = service.create(professor);
        log.info("POST /professores -> Cadastrado");

        log.info("POST /professores -> 201 CREATED");
        log.debug("POST /professores -> Response Body:\n{}\n", JsonUtil.objetoParaJson(professor.toString()));
        return ResponseEntity.status(HttpStatus.CREATED).body(NovoProfessordto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProfessorDto> update(@PathVariable(name = "id") Long id, @RequestBody RequestProfessorDto dto){
        log.info("PUT /professores/{}", id);

        ResponseProfessorDto professor = service.update(id, dto);
        log.info("PUT /professores/{} -> Atualizado", id);

        log.info("PUT /professores/{} -> 200 OK", id);
        log.debug("PUT /professores/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(professor.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("DELETE /professores/{}", id);

        service.delete(id);
        log.info("DELETE /professores/{} -> Excluído", id);

        log.info("DELETE /professores/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
