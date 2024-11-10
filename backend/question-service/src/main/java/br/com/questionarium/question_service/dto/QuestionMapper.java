package br.com.questionarium.question_service.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.questionarium.question_service.model.EducationLevel;
import br.com.questionarium.question_service.model.Question;
import br.com.questionarium.question_service.model.Tag;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(source = "educationLevel.id", target = "educationLevelId")
    @Mapping(source = "tags", target = "tagIds", qualifiedByName = "mapTagsToIds")
    QuestionDTO toDTO(Question question);

    @Mapping(source = "educationLevelId", target = "educationLevel", qualifiedByName = "mapLongToEducationLevel")
    @Mapping(source = "tagIds", target = "tags", qualifiedByName = "mapIdsToTags")
    Question toEntity(QuestionDTO questionDTO);

    @Named("mapTagsToIds")
    default Set<Long> mapTagsToIds(Set<Tag> tags) {
        return tags != null ? tags.stream().map(Tag::getId).collect(Collectors.toSet()) : new HashSet<>();
    }

    @Named("mapIdsToTags")
    default Set<Tag> mapIdsToTags(Set<Long> tagIds) {
        if (tagIds == null) return new HashSet<>();
        return tagIds.stream().map(id -> {
            Tag tag = new Tag();
            tag.setId(id);
            return tag;
        }).collect(Collectors.toSet());
    }

    @Named("mapLongToEducationLevel")
    default EducationLevel mapLongToEducationLevel(Long value) {
        if (value == null) {
            return null;
        }
        EducationLevel educationLevel = new EducationLevel();
        educationLevel.setId(value);
        return educationLevel;
    }
}
