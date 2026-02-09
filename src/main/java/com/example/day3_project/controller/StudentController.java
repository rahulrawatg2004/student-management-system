package com.example.day3_project.controller;

import com.example.day3_project.dto.StudentPatchRequestDto;
import com.example.day3_project.dto.StudentRequestDto;
import com.example.day3_project.dto.StudentResponseDto;
import com.example.day3_project.model.StudentModel;
import com.example.day3_project.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    private final StudentService service;
    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/add-student")
    public StudentResponseDto addStudent(@Valid  @RequestBody StudentRequestDto student) {

        return service.addStudent(student);
    }

    @GetMapping("/students")
    public List<StudentResponseDto> getStudents(){

        return service.getAllStudents();
    }

    @PutMapping("/update/{id}")
    public StudentResponseDto updateStudent(@PathVariable String id, @RequestBody StudentRequestDto student) {
        return service.updateStudent(id,student);
    }
    @PatchMapping("/patch/{id}")
    public StudentResponseDto patchStudent(@PathVariable String id, @Valid @RequestBody StudentPatchRequestDto student) {
        return service.patchStudent(id, student);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable String id) {
        service.deleteStudent(id);
    }

}
