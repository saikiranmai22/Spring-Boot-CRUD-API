package com.example.demo.student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//write business logic - they contain rules/logic about how students should be managed.
@Service //Spring will automatically create an object (bean) of this class.
public class StudentService {

    //Spring will automatically provide the studentRespository object to this class.
    private final StudentRespository studentRespository;
    //Constructor-based dependency injection.
    public StudentService(StudentRespository studentRespository) {
        this.studentRespository = studentRespository;
    }

    @Autowired
    public List<Student> getStudents(){ //restful endpoint
        return studentRespository.findAll();
    }

    //method from student controller
    public void addNewStudent(Student student) {
        //optional avoids null pointer exceptions- It represents a value that might exist or might NOT exist
        //it is a wrapper class
        Optional<Student> studentOptional=studentRespository
                .findStudentByEmail((student.getEmail())); //gets the each from the student object u want to add and checks the database to see if the email exists
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRespository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists =studentRespository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id "+studentId+" does not exist");

        }
        studentRespository.deleteById(studentId);
    }
    //A transaction ensures all operations inside it succeed or fail together.
    @Transactional //Keeps DB update actions in one transaction
    public void updateStudent(Long studentId, String name, String email) {
        Student student= studentRespository.findById(studentId).orElseThrow(()->new IllegalStateException(
                "student with id "+studentId+" does not exist")
        );
        if(name!=null && name.length()>0 && !Objects.equals(student.getName(),name)){
            student.setName(name); //same name can exist
        }

        if(email!=null && email.length()>0 && !Objects.equals(student.getEmail(),email)){
            Optional<Student> studentOptional= studentRespository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken"); //by other people
            }
            student.setEmail(email);
        }
    }
}

//Because StudentRepository extends JpaRepository, and all repositories automatically become Spring beans.
//So Spring creates a StudentRepository bean and injects it into StudentService
