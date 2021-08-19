package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.address.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AddressCustomRepository {
    List<Address> findAddressesByStudentId(int studentId);

    List<Address> getAddressByCity(String city);

    Long getStudentsCountByCity(String city);
}
