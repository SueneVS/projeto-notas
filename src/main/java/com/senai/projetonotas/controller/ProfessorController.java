package com.senai.projetonotas.controller;


import com.senai.projetonotas.dto.RequestProfessorDto;
import com.senai.projetonotas.dto.ResponseProfessorDto;
import com.senai.projetonotas.entity.ProfessorEntity;
import com.senai.projetonotas.service.ColecaoService;
import com.senai.projetonotas.service.ProfessorService;
import com.senai.projetonotas.service.impl.ProfessorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ColecaoService colecaoService) {
        this.service = colecaoService.getProfessorService();
    }

    @GetMapping
    public ResponseEntity<List<ResponseProfessorDto>> getEntities(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getEntitiesDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProfessorDto> getEntity(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getEntityDto(id));
    }

    @PostMapping
    public ResponseEntity<ResponseProfessorDto> create(@RequestBody RequestProfessorDto professor){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProfessorDto> update(@RequestBody RequestProfessorDto professor, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.getEntity(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
