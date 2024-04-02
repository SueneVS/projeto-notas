package com.senai.projetonotas.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "matricula")
public class MatriculaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_id", nullable = false)
    private long matriculaId;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_matricula", nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate dataMatricula;

    @Column(name = "media_final", nullable = false)
    @ColumnDefault("0.00")
    private double mediaFinal;

    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "disciplina_id", nullable = false) // Configura a coluna do banco de dados
    private DisciplinaEntity disciplina;

    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "aluno_id", nullable = false) // Configura a coluna do banco de dados
    private AlunoEntity aluno;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<NotaEntity> notas;


}
