package com.senai.projetonotas.repository;

import com.senai.projetonotas.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<MatriculaEntity, Long> {
    List<MatriculaEntity> findAllByDisciplinaDisciplinaId(Long id);
    List<MatriculaEntity> findAllByAlunoAlunoId(Long id);
}
