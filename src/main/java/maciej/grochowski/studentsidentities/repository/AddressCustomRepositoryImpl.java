package maciej.grochowski.studentsidentities.repository;

import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Address_;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@NoArgsConstructor
public class AddressCustomRepositoryImpl implements AddressCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Address> findAddressesByStudentId(int studentId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> address = criteriaQuery.from(Address.class);

        Predicate idPredicate = criteriaBuilder.equal(address.get(Address_.STUDENT), studentId);
        criteriaQuery.where(idPredicate);

        TypedQuery<Address> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    public Long countStudentsFromCity(String city) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Address> addressRoot = criteriaQuery.from(Address.class);

        Predicate cityPredicate = criteriaBuilder.like(addressRoot.get(Address_.CITY), city);
        criteriaQuery.select(criteriaBuilder.count(addressRoot)).where(cityPredicate);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Address> getAddressByCity(String city) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> address = criteriaQuery.from(Address.class);

        Predicate cityPredicate = criteriaBuilder.like(address.get(Address_.CITY), city);
        criteriaQuery.where(cityPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Address getAddressById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> address = criteriaQuery.from(Address.class);

        Predicate idPredicate = criteriaBuilder.equal(address.get(Address_.ID), id);
        criteriaQuery.where(idPredicate);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    @Transactional
    public void updateCity(int id, String cityUpdated) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<Address> update = criteriaBuilder.createCriteriaUpdate(Address.class);
        Root root = update.from(Address.class);

        update.set(Address_.CITY, cityUpdated);
        update.where(criteriaBuilder.equal(root.get(Address_.ID), id));

        this.entityManager.createQuery(update).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAddressById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<Address> delete = criteriaBuilder.createCriteriaDelete(Address.class);
        Root<Address> root = delete.from(Address.class);

        delete.where(criteriaBuilder.equal(root.get(Address_.ID), id));
        this.entityManager.createQuery(delete).executeUpdate();
    }
}
