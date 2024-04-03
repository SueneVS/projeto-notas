package com.senai.projetonotas.controller;


import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.service.impl.ProfessorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorServiceImpl service;

    @GetMapping
    public ResponseEntity<List<ProfessorEntity>> getEntities(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getEntities());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfessorEntity> getEntity(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getEntity(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorEntity> create(@RequestBody ProfessorEntity professor){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(professor));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProfessorEntity> update(@RequestBody ProfessorEntity professor, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, professor));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.getEntity(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
