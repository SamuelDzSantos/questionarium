package br.com.questionarium.question_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.questionarium.question_service.dto.QuestionDTO;
import br.com.questionarium.question_service.dto.QuestionMapper;
import br.com.questionarium.question_service.model.Question;
import br.com.questionarium.question_service.model.Tag;
import br.com.questionarium.question_service.repository.QuestionRepository;
import br.com.questionarium.question_service.repository.TagRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository,
            TagRepository tagRepository,
            QuestionMapper questionMapper) {

        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
        this.questionMapper = questionMapper;
    }

    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = questionMapper.toEntity(questionDTO);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(savedQuestion);
    }

    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(questionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<QuestionDTO> getQuestionById(Long id) {
        return questionRepository.findById(id)
                .map(questionMapper::toDTO);
    }

    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        return questionRepository.findById(id)
                .map(question -> {
                    question.setMultipleChoice(questionDTO.isMultipleChoice());
                    question.setNumberLines(questionDTO.getNumberLines());
                    question.setPersonId(questionDTO.getPersonId());
                    question.setHeaderId(questionDTO.getHeaderId());
                    question.setAnswerId(questionDTO.getAnswerId());
                    question.setEnable(questionDTO.isEnable());
                    question.setAccessLevel(questionDTO.getAccessLevel());
                    question.setEducationLevel(questionDTO.getEducationLevel());
                    
                    QuestionServiceHelper.setTags(questionDTO, question, tagRepository);

                    Question updatedQuestion = questionRepository.save(question);
                    
                    return questionMapper.toDTO(updatedQuestion);
                })
                .orElseThrow(() -> new RuntimeException("Question not found with ID " + id));
    }

    public void deleteQuestion(Long id) {
        questionRepository.findById(id)
            .map(question -> {
                question.setEnable(false);

                Question updatedQuestion = questionRepository.save(question);
                return updatedQuestion;
            })
            .orElseThrow(() -> new RuntimeException("Question not found with ID " + id));
    }

}

class QuestionServiceHelper {

    public static void setTags(QuestionDTO questionDTO, Question question, TagRepository tagRepository) {
        if (questionDTO.getTagIds() != null && !questionDTO.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Long tagId : questionDTO.getTagIds()) {
                Optional<Tag> tagOpt = tagRepository.findById(tagId);
                tagOpt.ifPresent(tags::add);
            }
            question.setTags(tags);
        }
    }
}