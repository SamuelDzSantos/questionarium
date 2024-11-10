package br.com.questionarium.question_service.init;

import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.questionarium.question_service.dto.EducationLevelDTO;
import br.com.questionarium.question_service.dto.QuestionDTO;
import br.com.questionarium.question_service.dto.TagDTO;
import br.com.questionarium.question_service.service.EducationLevelService;
import br.com.questionarium.question_service.service.QuestionService;
import br.com.questionarium.question_service.service.TagService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class dataLoader implements ApplicationRunner {

    private final QuestionService questionService;
    private final TagService tagService;
    private final EducationLevelService educationLevelService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        tagService.createTag(TagDTO.builder()
            .name("TI")
            .description("Tecnologia da Informação")
            .build());

        educationLevelService.createEducationLevel(EducationLevelDTO.builder()
            .name("Ensino Superior")
            .description("Bacharelado, Licenciatura, Tecnológo, e mais")
            .build());    

        questionService.createQuestion(QuestionDTO.builder()
            .multipleChoice(true)
            .numberLines(15)
            .educationLevelId(1L)
            .personId(0L)
            .headerId(0L)
            .answerId(0L)
            .enable(true)
            .tagIds(Set.of(1L))
            .build());
    }

}