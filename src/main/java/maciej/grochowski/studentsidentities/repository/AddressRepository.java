package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer>, AddressCustomRepository {
}
