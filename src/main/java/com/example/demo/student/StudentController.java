package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Rest controller tells the spring boot that this class will have all rest api endpoints
@RestController //this mapping makes this class serve rest endpoints
//        Combines:
//        @Controller → marks the class as a Spring MVC controller
//        @ResponseBody → returns data (JSON) directly instead of HTML pages
@RequestMapping(path = "api/v1/student") //Base URL for all endpoints in this controller.

public class StudentController {

    //a variable that holds the student service object
    private final StudentService studentService;

    //tells spring , give me an object of studentservice here, i need it
    //spring then injects the studentservice object into the controller- this procees is called dependency injection
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //endpoints
    //annotate
    @GetMapping //fetches all the students
    public List<Student> getStudents(){ //restful endpoint
        return studentService.getStudents();
    }

    @PostMapping //to add new resources
    /// request body- takes json converts to student object
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    //deletes by id, path variable- extracts the id value from the url
    @DeleteMapping(path="{studentId}")
    public void deleteStudent(
            @PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
    //PUT /api/v1/student/{studentId}?name=NewName&email=new@gmail.com
    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name, //takes individual values from url
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);//So you can update one field or both.
    }
}