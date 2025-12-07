package com.example.demo.student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRespository studentRespository;

    public StudentService(StudentRespository studentRespository) {
        this.studentRespository = studentRespository;
    }

    @Autowired
    public List<Student> getStudents(){ //restful endpoint
        return studentRespository.findAll();
    }

    //method from student controller
    public void addNewStudent(Student student) {
        Optional<Student> studentOptional=studentRespository
                .findStudentByEmail((student.getEmail()));
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

    @Transactional
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
