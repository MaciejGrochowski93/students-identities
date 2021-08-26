package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.AddressType;
import maciej.grochowski.studentsidentities.entity.Address;
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

    public List<Address> createAddressListFromTransfer(AddressListTransfer addressListTransfer) {
        List<AddressCreationDTO> addressCreationDTOList = addressListTransfer.initListOfThreeAddressDTO();
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
        initAddressTypesOnAddressList(addressList);
        return addressList;
    }

    private void initAddressTypesOnAddressList(List<Address> addressList) {
        if (addressList.contains(addressList.get(0))) addressList.get(0).setType(AddressType.PERMANENT);
        if (addressList.contains(addressList.get(1))) addressList.get(1).setType(AddressType.RESIDENTIAL);
        if (addressList.contains(addressList.get(2))) addressList.get(2).setType(AddressType.CORRESPONDENCE);
    }

    public Long countStudentsFromCity(String city) {
        return addressRepository.countStudentsFromCity(city);
    }

    public List<Address> getAddressByCity(String city) {
        return addressRepository.getAddressByCity(city);
    }

    public List<Address> findAddressesByStudentId(int id) {
        return addressRepository.findAddressesByStudentId(id);
    }

    public void sortByCity() {
        addressRepository.sortByCity();
    }

    public void sortByStreet() {
        addressRepository.sortByStreet();
    }

    public void sortByHouseNr() {
        addressRepository.sortByHouseNr();
    }

    public void sortByPostalCode() {
        addressRepository.sortByPostalCode();
    }

    public void deleteAddressById(int id) {
        addressRepository.deleteAddressById(id);
    }

    public void updateCity(int id, String city) {
        addressRepository.updateCity(id, city);
    }
}
