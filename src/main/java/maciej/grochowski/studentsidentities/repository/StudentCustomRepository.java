package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface StudentCustomRepository {

    List<Student> getAllStudentsCriteria();

    void sortByFirstName();

    void sortByMiddleName();

    void sortByLastName();

    void sortByAge();

    Long countStudentsOfAge(int age);

    List<Student> getStudentsFromCity2222(String city);

    List<Student> getStudentsFromCity3333(String city);

    Student getAddressById(int id);

    @Transactional
    void updateFirstName(int id, String firstNameUpdated);

    @Transactional
    void deleteStudentById(int id);
}
