package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.model.StudentSearchCriteria;
import maciej.grochowski.studentsidentities.model.StudentSortCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentCustomRepository {


    List<Student> findStudentsOfAge(int age);
}
