package br.com.questionarium.question_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.questionarium.question_service.dto.EducationLevelDTO;
import br.com.questionarium.question_service.model.EducationLevel;
import br.com.questionarium.question_service.repository.EducationLevelRepository;

@Service
public class EducationLevelService {

    private final EducationLevelRepository educationLevelRepository;

    public EducationLevelService(EducationLevelRepository educationLevelRepository) {
        this.educationLevelRepository = educationLevelRepository;
    }

    public EducationLevelDTO createEducationLevel(EducationLevelDTO educationLevelDTO) {
        EducationLevel educationLevel = new EducationLevel();
        educationLevel.setName(educationLevelDTO.getName());
        educationLevel.setDescription(educationLevelDTO.getDescription());
        educationLevel = educationLevelRepository.save(educationLevel);
        
        educationLevelDTO.setId(educationLevel.getId());
        return educationLevelDTO;
    }

    public List<EducationLevelDTO> getAllEducationLevels() {
        return educationLevelRepository.findAll()
                .stream()
                .map(educationLevel -> new EducationLevelDTO(educationLevel.getId(), educationLevel.getName(), educationLevel.getDescription()))
                .toList();
    }

    public EducationLevelDTO getEducationLevelById(Long id) {
        Optional<EducationLevel> educationLevelOpt = educationLevelRepository.findById(id);
        if (educationLevelOpt.isPresent()) {
            EducationLevel educationLevel = educationLevelOpt.get();
            return new EducationLevelDTO(educationLevel.getId(), educationLevel.getName(), educationLevel.getDescription());
        } else {
            throw new RuntimeException("EducationLevel not found with ID " + id);
        }
    }

    public EducationLevelDTO updateEducationLevel(Long id, EducationLevelDTO educationLevelDTO) {
        Optional<EducationLevel> educationLevelOpt = educationLevelRepository.findById(id);
        if (educationLevelOpt.isPresent()) {
            EducationLevel educationLevel = educationLevelOpt.get();
            educationLevel.setName(educationLevelDTO.getName());
            educationLevel.setDescription(educationLevelDTO.getDescription());
            educationLevel = educationLevelRepository.save(educationLevel);
            
            educationLevelDTO.setId(educationLevel.getId());
            return educationLevelDTO;
        } else {
            throw new RuntimeException("EducationLevel not found with ID " + id);
        }
    }

    public void deleteEducationLevel(Long id) {
        if (educationLevelRepository.existsById(id)) {
            educationLevelRepository.deleteById(id);
        } else {
            throw new RuntimeException("EducationLevel not found with ID " + id);
        }
    }
}