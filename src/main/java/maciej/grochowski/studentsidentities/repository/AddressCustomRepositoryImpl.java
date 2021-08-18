package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.address.Address;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class AddressCustomRepositoryImpl implements AddressCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Address> findAddressesByStudentId(int studentId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery <Address> cq = cb.createQuery(Address.class);

        Root<Address> address = cq.from(Address.class);

        Predicate studentIdPredicate = cb.equal(address.get("student"), studentId);

        cq.where(studentIdPredicate);

        TypedQuery<Address> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<Address> getAddressByCity(String city) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);

        Root<Address> address = cq.from(Address.class);

        Predicate studentsByCity = cb.like(address.get("city"), "%" + city + "%");
        cq.where(studentsByCity);

        TypedQuery<Address> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
