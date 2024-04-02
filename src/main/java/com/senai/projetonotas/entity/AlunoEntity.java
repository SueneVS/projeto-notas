package com.senai.projetonotas.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "aluno")
public class AlunoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aluno_id", nullable = false)
    private long alunoId;

    @Column(name = "nome", length = 150,nullable = false)
    private String nome;


    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<MatriculaEntity> matriculas;
}
