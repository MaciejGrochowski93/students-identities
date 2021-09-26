package maciej.grochowski.studentsidentities.repository;

import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Address_;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.entity.Student_;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;

@NoArgsConstructor
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Student getStudentByID(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> student = criteriaQuery.from(Student.class);

        Predicate idPredicate = criteriaBuilder.equal(student.get(Student_.ID), id);
        criteriaQuery.where(idPredicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteStudentById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<Student> delete = criteriaBuilder.createCriteriaDelete(Student.class);
        Root<Student> root = delete.from(Student.class);

        delete.where(criteriaBuilder.equal(root.get(Student_.ID), id));
        this.entityManager.createQuery(delete).executeUpdate();
    }


    @Override
    public Long countStudentsOfAge(int age) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        LocalDate tmpDate = LocalDate.now().minusYears(age);
        int yearOfBirth = tmpDate.getYear();

        LocalDate searchFromDate = LocalDate.of(yearOfBirth, 1, 1);
        LocalDate searchToDate = LocalDate.of(yearOfBirth, 12, 31);
        Predicate dobPredicate = criteriaBuilder.between(studentRoot.get(Student_.DOB), searchFromDate, searchToDate);

        criteriaQuery.select(criteriaBuilder.count(studentRoot)).where(dobPredicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getSingleResult();
    }
}