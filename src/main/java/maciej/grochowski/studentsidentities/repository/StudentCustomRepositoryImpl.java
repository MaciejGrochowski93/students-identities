package maciej.grochowski.studentsidentities.repository;

import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Address_;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.entity.Student_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class StudentCustomRepositoryImpl implements StudentCustomRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;

    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String criteriaWord;
    private int number = 1;

    @Override
    public List<Student> getAllStudentsCriteria() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> studentRoot = criteriaQuery.from(Student.class);

        Predicate predicate = studentRoot.isNotNull();
        criteriaQuery.where(predicate);

        setCriteriaWord();
        if (sortDirection.isAscending()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(studentRoot.get(criteriaWord)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(studentRoot.get(criteriaWord)));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void setCriteriaWord() {
        switch (number) {
            case 2:
                criteriaWord = "middleName";
                break;
            case 3:
                criteriaWord = "lastName";
                break;
            case 4:
                criteriaWord = "dob";
                break;
            default: criteriaWord = "firstName";
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
    public void sortByMiddleName() {
        number = 2;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByLastName() {
        number = 3;
        if (sortDirection.isAscending()) {
            sortDirection = Sort.Direction.DESC;
        } else {
            sortDirection = Sort.Direction.ASC;
        }
    }

    @Override
    public void sortByAge() {
        number = 4;
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

    @Override
    public List<Student> getStudentsFromCity2222(String city) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class); // co chcę uzyskać, określa wynik
        Root<Student> root = criteriaQuery.from(Student.class);                             // co przeglądam

        Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);             // co chcę uzyskać
        Root<Address> subRoot = subQuery.from(Address.class);                           // co przeglądam
        Join<Address, Student> subStudents = subRoot.join(Address_.student);            // łączę tabele

        subQuery.select(criteriaBuilder.count(subRoot.get(Address_.id)));             // policz studentów mających adress
        subQuery.where(criteriaBuilder.equal(root.get(Student_.id), subStudents.get(Student_.id))); // ze wspólnej listy
        criteriaQuery.where(criteriaBuilder.greaterThan(subQuery, 4L));         // gdzie POWYŻSZE jest większe niż 4

        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public List<Student> getStudentsFromCity3333(String city) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class); // co chcę uzyskać, określa wynik
        Root<Student> root = criteriaQuery.from(Student.class);                             // co przeglądam

        Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);             // co chcę uzyskać
        Root<Address> subRoot = subQuery.from(Address.class);                           // co przeglądam
        Join<Address, Student> subStudents = subRoot.join(Address_.student);            // łączę tabele

        subQuery.select(criteriaBuilder.count(subRoot.get(Address_.id)));   // policz studentów mających adress
        subQuery.where(criteriaBuilder.equal(root.get(Student_.id), subStudents.get(Student_.id))); // ze wspólnej listy
        subQuery.where(criteriaBuilder.equal(subRoot.get(Address_.CITY), city));

//        Predicate cityPredicate = criteriaBuilder.equal(subRoot.get(Address_.CITY), city); //

        criteriaQuery.where(criteriaBuilder.equal(root.get(Student_.ADDRESS_LIST), subQuery));

        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
}

    @Override
    public Student getAddressById(int id) {
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