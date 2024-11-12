package br.com.questionarium.question_service.init;

import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.questionarium.question_service.dto.QuestionDTO;
import br.com.questionarium.question_service.dto.TagDTO;
import br.com.questionarium.question_service.service.QuestionService;
import br.com.questionarium.question_service.service.TagService;
import br.com.questionarium.question_service.types.QuestionAccessLevel;
import br.com.questionarium.question_service.types.QuestionEducationLevel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class dataLoader implements ApplicationRunner {

    private final QuestionService questionService;
    private final TagService tagService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        tagService.createTag(TagDTO.builder()
            .name("TI")
            .description("Tecnologia da Informação")
            .build());

        questionService.createQuestion(QuestionDTO.builder()
            .multipleChoice(true)
            .numberLines(15)
            .educationLevel(QuestionEducationLevel.ENSINO_FUNDAMENTAL)
            .personId(0L)
            .headerId(0L)
            .answerId(0L)
            .enable(true)
            .accessLevel(QuestionAccessLevel.PRIVATE)
            .tagIds(Set.of(1L))
            .build());
    }

}