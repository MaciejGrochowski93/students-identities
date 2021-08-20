package maciej.grochowski.studentsidentities.service;

import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private final StudentUtils utils = new StudentUtils();

    public void addStudent(Student student) {
        if (utils.isPeselValid(student) && utils.isAdult(student)) {
            studentRepository.save(student);
        }
    }

    public void sortByFirstName() {
        studentRepository.sortByFirstName();
    }

    public List<Student> getAllStudentsCriteria() {
        return studentRepository.getAllStudentsCriteria();
    }

    public void sortByLastName() {
        studentRepository.sortByLastName();
    }

    public void sortByAge() {
        studentRepository.sortByAge();
    }
}

