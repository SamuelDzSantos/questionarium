package br.com.questionarium.evaluation_service.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation_header")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;

    private String department;

    private String course;

    private String classroom;

    private String professor; //NOME QUE APARECE NO CABECALHO

    @Lob
    private String instructions;

    @Lob
    private byte[] image;

    private LocalDate creationDate;

    @Column(nullable = false)
    private Long userId; // FK do usuário que criou
}
