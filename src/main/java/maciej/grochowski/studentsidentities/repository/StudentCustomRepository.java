package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentCustomRepository {

    List<Student> getAllStudentsCriteria();

    void sortByFirstName();

    void sortByLastName();

    void sortByAge();

    Long countStudentsOfAge(int age);
}
