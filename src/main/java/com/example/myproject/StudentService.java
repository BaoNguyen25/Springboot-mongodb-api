package com.example.myproject;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByEmail(String email) throws IllegalAccessException {
        Optional<Student> student =  studentRepository.findStudentByEmail(email);
        if(student.isPresent()) {
            return student;
        }
        else {
            throw new IllegalAccessException("The student with email " + email + " doesn't exists");
        }
    }

}
