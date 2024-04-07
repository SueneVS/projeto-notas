package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.MediasAlunoDto;
import com.senai.projetonotas.dto.RequestMatriculaDto;
import com.senai.projetonotas.dto.ResponseMatriculaDto;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id")Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseMatriculaDto> getEntity(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getEntityDto(id));
    }


    @GetMapping()
    public ResponseEntity<List<MatriculaEntity>> getEntities() {
        return ResponseEntity.ok(service.getEntities());
    }


    @GetMapping("/alunos/{id}")
    public ResponseEntity<List<ResponseMatriculaDto>> getEntitiesAluno(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getEntitiesAlunoDto(id));
    }

    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<List<ResponseMatriculaDto>> getEntitiesDisciplina(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getEntitiesDisciplinaDto(id));
    }


    @GetMapping("/aluno/{id}/media")
    public ResponseEntity<MediasAlunoDto> getMediasAluno(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getMediasAluno(id));
    }

}
