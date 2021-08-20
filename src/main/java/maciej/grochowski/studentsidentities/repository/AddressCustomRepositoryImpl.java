package maciej.grochowski.studentsidentities.repository;

import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.address.Address;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@NoArgsConstructor
public class AddressCustomRepositoryImpl implements AddressCustomRepository {

    @Autowired
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;


    public AddressCustomRepositoryImpl(EntityManager entityManager, CriteriaBuilder criteriaBuilder) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Address> findAddressesByStudentId(int studentId) {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> address = criteriaQuery.from(Address.class);

        Predicate idPredicate = criteriaBuilder.equal(address.get("student"), studentId);
        criteriaQuery.where(idPredicate);

        TypedQuery<Address> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public Long getStudentsCountByCity(String city) {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Address> addressRoot = criteriaQuery.from(Address.class);

        Predicate cityPredicate = criteriaBuilder.like(addressRoot.get("city"), city);
        criteriaQuery.select(criteriaBuilder.count(addressRoot)).where(cityPredicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Address> getAddressByCity(String city) {
        criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> address = criteriaQuery.from(Address.class);

        Predicate cityPredicate = criteriaBuilder.like(address.get("city"), city);
        criteriaQuery.where(cityPredicate);

        TypedQuery<Address> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
