package com.example.day3_project.service;

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
    public StudentModel addStudent(StudentModel student) {

        return repository.save(student);
    }

    //Display Students
    public List<StudentModel> getAllStudents() {
        return repository.findAll();
    }

    //Update student
    public StudentModel updateStudent(String id, StudentModel student) {
        StudentModel existingStudent = repository.findById(id).orElseThrow(() -> new RuntimeException("student not found"));
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());
        return repository.save(existingStudent);
    }

    //delete
    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
}