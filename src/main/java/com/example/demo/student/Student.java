package com.example.demo.student;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity //Entity tells Hibernate:"This class should be stored in the database as a table."
@Table (name = "student")

public class Student {
    //configure auto-generation of primary keys using a database sequence.
    @Id
    @SequenceGenerator(
            name = "student_sequence",//reference name inside the code.
            sequenceName = "student_sequence",//actual name of the sequence created in the DB
            allocationSize=1 //generates ID one by one
    )
    @GeneratedValue( //Tells Hibernate to auto-generate the ID using the above sequence.
            strategy = GenerationType.SEQUENCE, //uses DB sequence.
            generator = "student_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    //age is not stored in database- it is computed on the fly
    @Transient
    private Integer age;

    public Student() { //default constructor -Required by Hibernate for object creation
    }
    public Student(Long id,
                   String name,
                   String email,
                   LocalDate dob) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }
    public Student(String name,
                   String email,
                   LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    //Getters and Setters
    //Provide access and modification for private fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    //print the object in a readable format
    @Override
    public String toString(){
        return "Student{ "+
                "\nid= "+id+
                "\nname= "+name+
                "\nemail= "+email+
                "\ndob= "+dob+
                "\nage= }"+age;
    }
}
