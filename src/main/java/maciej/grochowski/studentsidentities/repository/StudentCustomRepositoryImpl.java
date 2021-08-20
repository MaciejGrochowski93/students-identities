package maciej.grochowski.studentsidentities.repository;

import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @Autowired
    private EntityManager entityManager;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String searchByWord;
    private int number = 1;

    @Override
    public List<Student> getAllStudentsCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        Predicate predicate = studentRoot.isNotNull();
        criteriaQuery.where(predicate);

        setSearchByWord();
        if (sortDirection.isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(studentRoot.get(searchByWord)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(studentRoot.get(searchByWord)));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void setSearchByWord() {
        switch (number) {
            case 2:
                searchByWord = "lastName";
                break;
            case 3:
                searchByWord = "dob";
                break;
            default: searchByWord = "firstName";
        }
    }

    @Override
    public void sortByFirstName() {
        number = 1;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByLastName() {
        number = 2;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByAge() {
        number = 3;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public List<Student> findStudentsOfAge(int age) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        LocalDate tmpDate = LocalDate.now().minusYears(age);
        int yearOfBirth = tmpDate.getYear();

        LocalDate searchFromDate = LocalDate.of(yearOfBirth, 1, 1);
        LocalDate searchToDate = LocalDate.of(yearOfBirth, 12, 31);
        Predicate dob = criteriaBuilder.between(studentRoot.get("dob"), searchFromDate, searchToDate);

        criteriaQuery.where(dob);
        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}