package com.senai.projetonotas.repository;

import com.senai.projetonotas.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
}
