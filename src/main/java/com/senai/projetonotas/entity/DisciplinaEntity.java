package com.senai.projetonotas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "disciplina")
public class DisciplinaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disciplina_id", nullable = false)
    private long disciplinaId;

    @Column(name = "nome", length = 150,nullable = false)
    private String nome;


    @JsonIgnoreProperties("disciplinas")
    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "professor_id") // Configura a coluna do banco de dados
    private ProfessorEntity professor;

    @JsonIgnoreProperties("disciplina")
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<MatriculaEntity> matriculas;
}
