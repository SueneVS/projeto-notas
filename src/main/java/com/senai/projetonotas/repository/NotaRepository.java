package com.senai.projetonotas.repository;

import com.senai.projetonotas.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findAllByMatricula_MatriculaId(Long matriculaId);
}
