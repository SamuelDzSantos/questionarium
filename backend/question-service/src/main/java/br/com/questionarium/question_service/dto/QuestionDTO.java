package br.com.questionarium.question_service.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private boolean multipleChoice;
    private Integer numberLines;
    private Long educationLevelId;
    private Long personId;
    private Long headerId;
    private Long answerId;
    private boolean enable;

    private Set<Long> tagIds;
}