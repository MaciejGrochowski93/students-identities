package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.model.StudentSearchCriteria;
import maciej.grochowski.studentsidentities.model.StudentSortCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @Autowired
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public StudentCustomRepositoryImpl() {
    }

    public StudentCustomRepositoryImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Student> findAllWithFilters(StudentSearchCriteria searchCriteria, StudentSortCriteria sortCriteria) {
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        return null;
    }

    @Override
    public List<Student> findStudentsOfAge(int age) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        LocalDate tmpDate = LocalDate.now().minusYears(age);
        int yearOfBirth = tmpDate.getYear();

        LocalDate searchFromDate = LocalDate.of(yearOfBirth, 01, 01);
        LocalDate searchToDate = LocalDate.of(yearOfBirth, 12, 31);
        Predicate dob = criteriaBuilder.between(studentRoot.get("dob"), searchFromDate, searchToDate);

        criteriaQuery.where(dob);
        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}
