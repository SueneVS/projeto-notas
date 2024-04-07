package com.senai.projetonotas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "aluno")
@AllArgsConstructor
public class AlunoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aluno_id", nullable = false)
    private long alunoId;

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;


    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento", nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<MatriculaEntity> matriculas;

    public AlunoEntity(String nome, String dataNascimento){
        this.nome = nome;
        this.dataNascimento = LocalDate.parse(dataNascimento);
    }



}
