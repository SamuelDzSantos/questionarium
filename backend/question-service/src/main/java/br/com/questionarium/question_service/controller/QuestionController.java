package br.com.questionarium.question_service.controller;

import br.com.questionarium.question_service.dto.AnswerKeyDTO;
import br.com.questionarium.question_service.dto.QuestionDTO;
import br.com.questionarium.question_service.service.QuestionService;
import br.com.questionarium.question_service.types.QuestionAccessLevel;
import br.com.questionarium.question_service.types.QuestionEducationLevel;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    // @Autowired private AsyncRabbitTemplate template;

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

    @GetMapping("/answer-key")
    public ResponseEntity<List<AnswerKeyDTO>> getAnswerKeys(@RequestParam List<Long> questionIds){
        List<AnswerKeyDTO> list = questionService.getAnswerKeys(questionIds);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<List<QuestionDTO>> getListQuestions(@RequestParam List<Long> questionIds){
        List<QuestionDTO> list = questionService.getQuestionsByIds(questionIds);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
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

    @GetMapping("/filter")
    public ResponseEntity<List<QuestionDTO>> filterQuestions(
            @RequestParam(required = false) boolean multipleChoice,
            @RequestParam(required = false) Long personId,
            @RequestParam(required = false) Integer difficultyLevel,
            @RequestParam(required = false) QuestionEducationLevel educationLevel,
            @RequestParam(required = false) QuestionAccessLevel accessLevel,
            @RequestParam(required = false) Set<Long> tagIds) {

        List<QuestionDTO> list = questionService.getFilteredQuestions(multipleChoice, personId, difficultyLevel, educationLevel, accessLevel, tagIds);

        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // @GetMapping("test")
    // public Object test() {
    //     List<Integer> list = List.of(1, 2);

	// 		CompletableFuture<Object> f = template.convertSendAndReceive("default-exchange", 
	// 			"question.answer-key", 
	// 			list)
	// 		.toCompletableFuture();

	// 		return Mono.fromFuture(f).map(response -> {
	// 			try {
    //                 System.out.println("******************");
	// 				System.out.println(response);
	// 				return response;
	// 			} catch (Exception e) {
	// 				return null;
	// 			}
	// 		});
    // }
    

}
