package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> createAddressListFromDTO(List<AddressCreationDTO> addressCreationDTOList) {
        List<Address> addressList = new ArrayList<>();

        for (AddressCreationDTO addressDTO : addressCreationDTOList) {
            Address address = new Address();
            address.setCity(addressDTO.getCity());
            address.setStreet(addressDTO.getStreet());
            address.setHouseNr(addressDTO.getHouseNr());
            address.setPostalCode(addressDTO.getPostalCode());
            address.setType(addressDTO.getType());

            addressList.add(address);
        }
        return addressList;
    }

    public void saveAddressesOfStudent(Student student) {
        List<Address> addressList = student.getAddressList();
        addressList.forEach(address -> address.setStudent(student));
    }

    public Long countStudentsFromCity(String city) {
        return addressRepository.countStudentsFromCity(city);
    }
}
