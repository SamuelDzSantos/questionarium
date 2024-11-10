package br.com.questionarium.question_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EducationLevelDTO {
    private Long id;
    private String name;
    private String description;
}
