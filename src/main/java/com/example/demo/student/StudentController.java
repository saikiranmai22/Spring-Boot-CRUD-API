package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//this class will have all of the resources for the api
@RestController //this mapping makes this class serve rest endpoints
@RequestMapping(path = "api/v1/student")

public class StudentController {

    //a variable that holds the student service object
    private final StudentService studentService;

    //tells spring , give me an object of studentservice here, i need it
    //spring then injects the studentservice object into the controller- this procees is called dependency injection
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //annotate
    @GetMapping
    public List<Student> getStudents(){ //restful endpoint
        return studentService.getStudents();
    }

    @PostMapping //to add new resources
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);
    }
}