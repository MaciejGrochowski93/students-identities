package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface AddressRepository extends JpaRepository<Address, Integer>, AddressCustomRepository {

    Page<Address> findAllByStudentId(int id, Pageable pageable);
}
