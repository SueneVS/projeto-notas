package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.MediasAlunoDto;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("matriculas")
@RequiredArgsConstructor
public class MatriculaController {


    private final MatriculaService service;

    @PostMapping
    public ResponseEntity<MatriculaEntity> create(@RequestBody MatriculaEntity dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id")Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<MatriculaEntity> update(@PathVariable(name = "id") Long id,
                                                  @RequestBody MatriculaEntity dto) {
        return ResponseEntity.ok(service.update(id,dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<MatriculaEntity> getEntity(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getEntity(id));
    }


    @GetMapping()
    public ResponseEntity<List<MatriculaEntity>> getEntities() {
        return ResponseEntity.ok(service.getEntities());
    }
    @GetMapping("/aluno/{id}")
    public ResponseEntity<List<MatriculaEntity>> getEntitiesAluno(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getEntitiesAluno(id));
    }

    @GetMapping("/disciplina/{id}")
    public ResponseEntity<List<MatriculaEntity>> getEntitiesDisciplina(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getEntitiesDisciplina(id));
    }


    @GetMapping("/medias/aluno/{id}")
    public ResponseEntity<MediasAlunoDto> getMediasAluno(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getMediasAluno(id));
    }

}
