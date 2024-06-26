package com.senai.projetonotas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@Entity
@Table(name = "nota")
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnoreProperties({"disciplinas", "notas"})
    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "professor_id") // Configura a coluna do banco de dados
    private ProfessorEntity professor;

    @JsonIgnoreProperties({"disciplinas", "notas"})
    @ManyToOne(optional = false) // Indica que é uma chave estrangeira e não pode ser nula
    @JoinColumn(name = "matricula_id", nullable = false) // Configura a coluna do banco de dados
    private MatriculaEntity matricula;

    public NotaEntity(double nota, double coeficiente, ProfessorEntity professor, MatriculaEntity matricula) {
        this.nota =nota;
        this.coeficiente =coeficiente;
        this.professor = professor;
        this.matricula = matricula;
    }
}
