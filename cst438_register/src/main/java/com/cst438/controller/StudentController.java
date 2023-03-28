package com.cst438.controller;

import com.cst438.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    // this function is checking if the student id and student from the email in the dto match,
    // if they do we are good to update, then we perform an update by setting the value in the student repository
    @PutMapping("/student/{id}")
    @Transactional
    public void updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable("id") Integer studentId) {
        // check if valid to update student
        if (!checkStudent(studentId, studentDTO.studentEmail))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " email and Id don't match");
        // update the student status based off the student status in the DTO
        studentRepository.findByStudentId(studentId).setStatus(studentDTO.studentStatus);
    }

    @PostMapping("/student")
    @Transactional
    public Student addStudent(@RequestBody StudentDTO studentDTO) {
        // check if valid to create student
        if (studentRepository.findByEmail(studentDTO.studentEmail) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student exists");
        // create a new student with the info we are given and return the student
        Student student = new Student();
        student.setStatus(studentDTO.studentStatus);
        student.setName(studentDTO.studentName);
        student.setEmail(studentDTO.studentEmail);
        student.setStatusCode(0);
        return student;
    }

    // Need a method to check if both the Id and email retrieved students match, ideally they always should but if they don't need to throw an error
    private boolean checkStudent(int Id, String email) {
        Student idResult = studentRepository.findByStudentId(Id);
        Student emailResult = studentRepository.findByEmail(email);
        return emailResult.getStudent_id() == idResult.getStudent_id() && idResult.getEmail().equals(emailResult.getEmail());
    }

}
