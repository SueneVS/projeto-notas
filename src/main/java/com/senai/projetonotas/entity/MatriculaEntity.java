package com.senai.projetonotas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "matricula")
@AllArgsConstructor
@NoArgsConstructor
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

    public MatriculaEntity(AlunoEntity aluno,DisciplinaEntity disciplina){
        this.aluno = aluno;
        this.disciplina = disciplina;
    }

    public double somaCoeficiente(){
        double coeficiente = 0.0;
        for(NotaEntity nota: notas){
            coeficiente += nota.getCoeficiente();
        }
        return  coeficiente;
    }

}
