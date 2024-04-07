package com.senai.projetonotas.controller;

import com.senai.projetonotas.dto.RequestDisciplinaDto;
import com.senai.projetonotas.dto.ResponseDisciplinaDto;
import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import com.senai.projetonotas.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("disciplinas")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService service;

    @PostMapping
    public ResponseEntity<ResponseDisciplinaDto> create(@RequestBody RequestDisciplinaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDisciplinaDto> update(@PathVariable(name = "id") Long id, @RequestBody RequestDisciplinaDto dto) {
        return ResponseEntity.ok(service.update(id,dto));
    }

    @GetMapping()
    public ResponseEntity<List<DisciplinaEntity>> getEntities() {
        return ResponseEntity.ok(service.getEntities());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<DisciplinaEntity> getEntity(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.getEntity(id));
    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<List<MatriculaEntity>> getEntitiesProfessor(@PathVariable(name = "id")Long id) {
        return ResponseEntity.ok(service.getEntitiesProfessor(id));
    }


}
