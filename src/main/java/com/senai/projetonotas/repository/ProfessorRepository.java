package com.senai.projetonotas.repository;

import com.senai.projetonotas.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {
}
