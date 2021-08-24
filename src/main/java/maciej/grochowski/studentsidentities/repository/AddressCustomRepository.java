package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Address;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface AddressCustomRepository {
    List<Address> findAddressesByStudentId(int studentId);

    Address getAddressById(int id);

    @Transactional
    void updateCity(int id, String cityUpdated);

    @Transactional
    void deleteAddressById(int id);

    List<Address> getAddressByCity(String city);

    void sortByCity();

    void sortByStreet();

    void sortByHouseNr();

    void sortByPostalCode();

    Long countStudentsFromCity(String city);
}
