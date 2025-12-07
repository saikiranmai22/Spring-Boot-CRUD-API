package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRespository repository){
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
