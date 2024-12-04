package br.com.questionarium.question_service.init;

import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.questionarium.question_service.dto.AlternativeDTO;
import br.com.questionarium.question_service.dto.HeaderDTO;
import br.com.questionarium.question_service.dto.QuestionDTO;
import br.com.questionarium.question_service.dto.TagDTO;
import br.com.questionarium.question_service.service.HeaderService;
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
    private final HeaderService headerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        headerService.createHeader(HeaderDTO.builder()
            .content("Header")
            .imagePath("$path")
            .build()
        );

        tagService.createTag(TagDTO.builder()
            .name("TI")
            .description("Tecnologia da Informação")
            .build());

        questionService.createQuestion(QuestionDTO.builder()
            .multipleChoice(true)
            .numberLines(15)
            .educationLevel(QuestionEducationLevel.ENSINO_FUNDAMENTAL)
            .personId(0L)
            .header(HeaderDTO.builder().content("Em uma floresta, animais convivem em harmonia, mantendo o equilíbrio do ecossistema. Porém, uma mudança inesperada ameaça esse equilíbrio. Qual solução poderia restaurar a harmonia, considerando os impactos na biodiversidade e na sustentabilidade?").imagePath("$path").build())
            .answerId(0L)
            .enable(true)
            .accessLevel(QuestionAccessLevel.PRIVATE)
            .tagIds(Set.of(1L))
            .alternatives(Set.of(
                new AlternativeDTO(null, "A","A", "path",true, "a",null),
                new AlternativeDTO(null, "B","B", "path",false,"b",null),
                new AlternativeDTO(null, "C","C", "path",false,"c",null),
                new AlternativeDTO(null, "D","D", "path",false,"d",null),
                new AlternativeDTO(null, "E","E", "path",false,"e",null) ))
            .build());

            questionService.createQuestion(QuestionDTO.builder()
            .multipleChoice(true)
            .numberLines(66)
            .educationLevel(QuestionEducationLevel.ENSINO_MÉDIO)
            .personId(0L)
            .header(HeaderDTO.builder().content("Porém, uma mudança inesperada ameaça esse equilíbrio. Qual solução poderia restaurar a harmonia, considerando os impactos na biodiversidade e na sustentabilidade?").imagePath("$path").build())
            .answerId(0L)
            .enable(true)
            .accessLevel(QuestionAccessLevel.PRIVATE)
            .difficultyLevel(0)
            .tagIds(Set.of(1L))
            .alternatives(Set.of(
                new AlternativeDTO(null, "A","A", "path",false, "ff",null),
                new AlternativeDTO(null, "B","B", "path",false,"ff",null),
                new AlternativeDTO(null, "C","C", "path",true,"ff",null),
                new AlternativeDTO(null, "D","D", "path",false,"ff",null),
                new AlternativeDTO(null, "E","E", "path",false,"ff",null) ))
            .build());
    }

}