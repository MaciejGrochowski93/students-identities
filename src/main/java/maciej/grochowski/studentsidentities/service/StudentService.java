package maciej.grochowski.studentsidentities.service;

import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.StudentRepository2;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository2 studentRepository2;

    @Autowired
    private AddressService addressService;

    private final StudentUtils utils = new StudentUtils();

    public void addStudent(Student student) {
        if (utils.isPeselValid(student) && utils.isAdult(student)) {
            studentRepository2.save(student);
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository2.findAll();
    }
}

