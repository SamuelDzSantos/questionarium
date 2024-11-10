package br.com.questionarium.question_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.questionarium.question_service.dto.EducationLevelDTO;
import br.com.questionarium.question_service.service.EducationLevelService;

import java.util.List;

@RestController
@RequestMapping("/questions/education-level")
public class EducationLevelController {

    private final EducationLevelService educationLevelService;

    public EducationLevelController(EducationLevelService educationLevelService) {
        this.educationLevelService = educationLevelService;
    }

    @PostMapping
    public ResponseEntity<EducationLevelDTO> createEducationLevel(@RequestBody EducationLevelDTO educationLevelDTO) {
        EducationLevelDTO createdEducationLevel = educationLevelService.createEducationLevel(educationLevelDTO);
        return new ResponseEntity<>(createdEducationLevel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EducationLevelDTO>> getAllEducationLevels() {
        List<EducationLevelDTO> levels = educationLevelService.getAllEducationLevels();
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationLevelDTO> getEducationLevelById(@PathVariable Long id) {
        try {
            EducationLevelDTO educationLevelDTO = educationLevelService.getEducationLevelById(id);
            return new ResponseEntity<>(educationLevelDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationLevelDTO> updateEducationLevel(@PathVariable Long id, @RequestBody EducationLevelDTO educationLevelDTO) {
        try {
            EducationLevelDTO updatedEducationLevel = educationLevelService.updateEducationLevel(id, educationLevelDTO);
            return new ResponseEntity<>(updatedEducationLevel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducationLevel(@PathVariable Long id) {
        try {
            educationLevelService.deleteEducationLevel(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
