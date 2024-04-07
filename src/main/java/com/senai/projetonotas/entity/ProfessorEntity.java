package com.senai.projetonotas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "professor")
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id", nullable = false)
    private long professorId;

    @Column(name = "nome", length = 150,nullable = false)
    private String nome;

    @JsonIgnoreProperties({"professor", "matriculas"})
    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<DisciplinaEntity> disciplinas;

    @JsonIgnoreProperties({"professor", "matricula"})
    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<NotaEntity> notas;

    public ProfessorEntity(String nome){
        this.nome = nome;
    }
}
