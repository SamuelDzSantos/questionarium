package br.com.questionarium.question_service.repository;

import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import br.com.questionarium.question_service.model.Question;
import br.com.questionarium.question_service.types.QuestionAccessLevel;
import br.com.questionarium.question_service.types.QuestionEducationLevel;

public class QuestionSpecification {

    public static Specification<Question> filterByMultipleChoice(boolean multipleChoice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("multipleChoice"), multipleChoice);
    }

    public static Specification<Question> filterByPersonId(Long personId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("personId"), personId);
    }

    public static Specification<Question> filterByDifficultyLevel(Integer difficultyLevel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("difficultyLevel"), difficultyLevel);
    }

    public static Specification<Question> filterByEducationLevel(QuestionEducationLevel educationLevel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("educationLevel"), educationLevel);
    }

    public static Specification<Question> filterByAccessLevel(QuestionAccessLevel accessLevel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("accessLevel"), accessLevel);
    }

    public static Specification<Question> filterByEnableTrue() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("enable"));
    }

    public static Specification<Question> filterByTagIds(Set<Long> tagIds) {
        return (root, query, criteriaBuilder) -> {
            if (tagIds == null || tagIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("tags").get("id").in(tagIds);
        };
    }
}
