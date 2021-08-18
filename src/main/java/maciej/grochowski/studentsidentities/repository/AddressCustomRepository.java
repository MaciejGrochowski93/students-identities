package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.address.Address;

import java.util.List;

public interface AddressCustomRepository {
    List<Address> findAddressesByStudentId(int studentId);

    List<Address> getAddressByCity(String city);
}
