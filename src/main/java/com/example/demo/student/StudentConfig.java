package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
//toload data for testing

//This class pre-loads sample data into your database when the Spring Boot application starts.
@Configuration //This tells Spring:This class contains beans (objects) that should be created and managed by Spring Boot.
public class StudentConfig {

    @Bean //This tells Spring:Create and store the return value of this method as a Spring-managed bean.
    //CommandLineRunner is a functional interface that runs a piece of code after the application starts.
    CommandLineRunner commandLineRunner(StudentRespository repository){ //Spring automatically injects the repository object to us
        //This is a lambda expression (instead of writing a full class). It means:When the application starts, run the code inside
        return args -> {
            Student kiranmai = new Student(
                    "Kiranmai",
                    "kiranmai@gmail.com",
                    LocalDate.of(2004, Month.MAY,22)
            );
            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2004, Month.FEBRUARY,1)
            );

            repository.saveAll(
                    List.of(kiranmai,alex)
            );
        };
    }
}
//The repository was already a bean.
//The method creates a new bean of type CommandLineRunner.
//Spring passes the repository bean into that method so you can use it to save sample data.