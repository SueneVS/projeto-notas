package com.senai.projetonotas.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "professor")
public class ProfessorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id", nullable = false)
    private long professorId;

    @Column(name = "nome", length = 150,nullable = false)
    private String nome;



    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<DisciplinaEntity> disciplinas;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<NotaEntity> notas;
}
