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
    public List<Student> findStudentsOfAge(int age) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        LocalDate dateOfBirth = LocalDate.now().minusYears(age); // -28 = 1993
        int yearOfBirth = dateOfBirth.getYear();

        Predicate getAllStudentsPredicate = criteriaBuilder.like(studentRoot.get("firstName"), "%");
        criteriaQuery.where(getAllStudentsPredicate);

        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);

        return getSameYearsList(typedQuery, yearOfBirth);
    }

    public List<Student> getSameYearsList(TypedQuery typedQuery, int yearOfBirth) {
        List<Student> queryList = typedQuery.getResultList();
        List<Student> resultList = new ArrayList<>();

        for (Student result : queryList) {
            if (result.getDob().getYear() == yearOfBirth) {
                resultList.add(result);
            }
        }
        return resultList;
    }

}
