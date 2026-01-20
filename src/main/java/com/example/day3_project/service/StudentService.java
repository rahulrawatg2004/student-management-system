package com.example.day3_project.service;

import com.example.day3_project.model.StudentModel;
import com.example.day3_project.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private   final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    //CREATE


    public StudentModel addStudent(StudentModel student){
        return repository.save(student);
    }
}
