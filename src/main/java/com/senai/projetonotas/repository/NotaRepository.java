package com.senai.projetonotas.repository;

import com.senai.projetonotas.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
}
