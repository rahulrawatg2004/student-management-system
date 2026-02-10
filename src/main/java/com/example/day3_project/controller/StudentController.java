package com.example.day3_project.controller;

import com.example.day3_project.dto.StudentPatchRequestDto;
import com.example.day3_project.dto.StudentRequestDto;
import com.example.day3_project.dto.StudentResponseDto;
import com.example.day3_project.model.StudentModel;
import com.example.day3_project.service.StudentService;
import com.example.day3_project.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    private final StudentService service;
    private final JwtUtil jwtUtil;
    public StudentController(StudentService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }
    private void checkToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid Token");
        String token = authHeader.substring(7);
        jwtUtil.validateTokenAndGetEmail((token));
    }

    @PostMapping("/add-student")
    public StudentResponseDto addStudent(@RequestHeader("Authorization") String authHeader,@Valid  @RequestBody StudentRequestDto student) {
        checkToken(authHeader);
        return service.addStudent(student);
    }

    @GetMapping("/students")
    public List<StudentResponseDto> getStudents(@RequestHeader(value="Authorization", required=false) String authHeader) {
        checkToken(authHeader);
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
