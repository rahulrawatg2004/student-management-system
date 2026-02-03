package com.example.day3_project.service;

import com.example.day3_project.dto.StudentRequestDto;
import com.example.day3_project.dto.StudentResponseDto;
import com.example.day3_project.exception.StudentNotFound;
import com.example.day3_project.model.StudentModel;
import com.example.day3_project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    //CREATE
    public StudentResponseDto addStudent(StudentRequestDto dto) {
         StudentModel Student = new StudentModel();
         Student.setName(dto.getName());
         Student.setEmail(dto.getEmail());
         Student.setAge(dto.getAge());

         StudentModel saved = repository.save(Student);

         return new StudentResponseDto(
                 saved.getId(),
                 saved.getName(),
                 saved.getEmail(),
                 saved.getAge()
         );


    }

    //Display Students
    public List<StudentResponseDto> getAllStudents() {
        return repository.findAll()
                .stream().map(s -> new StudentResponseDto(
                     s.getId(),
                     s.getName(),
                     s.getEmail(),
                        s.getAge()
                )).toList();
    }

    //Update student
    public StudentResponseDto updateStudent(String id, StudentRequestDto student) {
        StudentModel existingStudent = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("student not found"));

        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());

        StudentModel savedStudent = repository.save(existingStudent);

        return new StudentResponseDto(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getAge()
        );
    }

    //delete
    public void deleteStudent(String id) {
        StudentModel student = repository.findById(id).orElseThrow(() -> new StudentNotFound("student not found"));
        repository.delete(student);
    }
}