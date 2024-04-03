package com.senai.projetonotas.repository;

import com.senai.projetonotas.entity.DisciplinaEntity;
import com.senai.projetonotas.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DisciplinaRepository extends JpaRepository<DisciplinaEntity, Long> {
    List<MatriculaEntity> findAllByProfessorProfessorId(Long id);
}
