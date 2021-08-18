package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.address.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public void saveAddressesOfStudent(Student student) {
        List<Address> addressList = student.getAddressList();
        addressList.forEach(address -> address.setStudent(student));
    }
}
