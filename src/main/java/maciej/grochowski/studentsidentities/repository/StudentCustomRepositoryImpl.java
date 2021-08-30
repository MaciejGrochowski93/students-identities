package maciej.grochowski.studentsidentities.repository;

import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Address_;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.entity.Student_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @Autowired
    private EntityManager entityManager;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String criteriaSearchWord;
    private int criteriaSortNumber = 1;

    @Override
    public List<Student> getAllStudentsByCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        Predicate predicate = studentRoot.isNotNull();
        criteriaQuery.where(predicate);

        setCriteriaSearchWord();
        if (sortDirection.isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(studentRoot.get(criteriaSearchWord)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(studentRoot.get(criteriaSearchWord)));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void setCriteriaSearchWord() {
        switch (criteriaSortNumber) {
            case 2:
                criteriaSearchWord = "middleName";
                break;
            case 3:
                criteriaSearchWord = "lastName";
                break;
            case 4:
                criteriaSearchWord = "dob";
                break;
            default:
                criteriaSearchWord = "firstName";
        }
    }

    @Override
    public void sortByFirstName() {
        criteriaSortNumber = 1;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByMiddleName() {
        criteriaSortNumber = 2;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByLastName() {
        criteriaSortNumber = 3;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByAge() {
        criteriaSortNumber = 4;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
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

//    @Override
//    public Long getStudentsFromCity2(String city) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class); // co chcę uzyskać, określa wynik
//        Root<Student> root = criteriaQuery.from(Student.class);                             // co przeglądam
//        Join<Object, Object> address = root.join(Student_.ADDRESS_LIST);
//
//        ParameterExpression<String> addressCity = criteriaBuilder.parameter(String.class);
//        criteriaQuery.where(criteriaBuilder.equal(address.get(Address_.CITY), addressCity));
//
//        criteriaQuery.select(criteriaBuilder.count(root));
//
//        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setParameter(addressCity, city);
//        return typedQuery.getSingleResult();
//    }

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
    public void updateFirstName(int id, String firstNameUpdated) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Student> update = criteriaBuilder.createCriteriaUpdate(Student.class);
        Root root = update.from(Student.class);

        update.set(Student_.firstName, firstNameUpdated);
        update.where(criteriaBuilder.equal(root.get(Address_.ID), id));

        this.entityManager.createQuery(update).executeUpdate();
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
}