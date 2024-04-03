package com.senai.projetonotas.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Table(name = "nota")
public class NotaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nota_id", nullable = false)
    private long notaId;


    @Column(nullable = false)
    @ColumnDefault(value = "0.00")
    private Double nota;

    @Column(nullable = false)
    @ColumnDefault(value = "0.00")
    private Double coeficiente;

    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "professor_id") // Configura a coluna do banco de dados
    private ProfessorEntity professor;

    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "matricula_id", nullable = false) // Configura a coluna do banco de dados
    private MatriculaEntity matricula;
}
