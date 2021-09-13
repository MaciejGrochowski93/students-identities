package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface StudentCustomRepository {

//    List<Student> getAllStudentsByCriteria();

//    void sortByFirstName();
//
//    void sortByMiddleName();
//
//    void sortByLastName();
//
//    void sortByAge();

    Long countStudentsOfAge(int age);

    Student getStudentByID(int id);

    @Transactional
    void updateFirstName(int id, String firstNameUpdated);

    @Transactional
    void deleteStudentById(int id);


//    Long getStudentsFromCity2(String city);



}
