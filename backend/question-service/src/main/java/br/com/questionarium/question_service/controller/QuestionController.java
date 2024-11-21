package br.com.questionarium.question_service.controller;

import br.com.questionarium.question_service.dto.QuestionDTO;
import br.com.questionarium.question_service.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO createdQuestion = questionService.createQuestion(questionDTO);
        return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        try {
            QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
            return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
