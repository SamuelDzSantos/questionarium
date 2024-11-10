package br.com.questionarium.question_service.model;

import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "questions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean multipleChoice;

    @Column(nullable = false)
    private Integer numberLines;

    @ManyToOne
    @JoinColumn(name = "education_level_id", referencedColumnName = "id", nullable = true )
    private EducationLevel educationLevel;

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @Column(name = "header_id", nullable = false)
    private Long headerId;

    @Column(name = "answer_id", nullable = false)
    private Long answerId;

    @Column(nullable = false)
    private boolean enable;

    @ManyToMany
    @JoinTable(
        name = "question_tags",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    

}
