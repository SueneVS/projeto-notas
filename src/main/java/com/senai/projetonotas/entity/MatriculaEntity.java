package com.senai.projetonotas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "matricula")
@RequiredArgsConstructor
public class MatriculaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula_id")
    private long matriculaId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_matricula")
    @ColumnDefault(value ="CURRENT_TIMESTAMP")
    private LocalDateTime dataMatricula;

    @Column(name = "media_final")
    @ColumnDefault(value ="0.00")
    private double mediaFinal;

    @JsonIgnoreProperties({"matriculas", "professor"})
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "disciplina_id") // Configura a coluna do banco de dados
    private DisciplinaEntity disciplina;

    @JsonIgnoreProperties("matriculas")
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "aluno_id") // Configura a coluna do banco de dados
    private AlunoEntity aluno;

    @JsonIgnoreProperties("matriculas")
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<NotaEntity> notas;


}
