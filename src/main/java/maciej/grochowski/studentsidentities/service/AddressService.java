package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.AddressType;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final StudentUtils utils;

    public AddressListTransfer initListTransfer() {
        AddressListTransfer addressListTransfer = new AddressListTransfer();
        List<AddressCreationDTO> addressCreationDTOS = addressListTransfer.initListOfThreeAddressDTO();
        addressListTransfer.setAddressDTOList(addressCreationDTOS);
        return addressListTransfer;
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
            addressList.add(address);
        }
        initAddressTypesOnAddressList(addressList);
        return addressList;
    }

    public AddressListTransfer createListTransferFromStudent(int studentId) {
        List<Address> addressList = findAddressesByStudentId(studentId);
        List<AddressCreationDTO> addressCreationDTOList = new ArrayList<>();

        for (Address address : addressList) {
            AddressCreationDTO DTO = new AddressCreationDTO();
            DTO.setCity(address.getCity());
            DTO.setStreet(address.getStreet());
            DTO.setHouseNr(address.getHouseNr());
            DTO.setPostalCode(address.getPostalCode());
            DTO.setType(address.getType());
            addressCreationDTOList.add(DTO);
        }
        AddressListTransfer listTransfer = new AddressListTransfer();
        listTransfer.setAddressDTOList(addressCreationDTOList);
        return listTransfer;
    }

    public void updateAddressesOfStudentId(int studentId, AddressListTransfer listTransfer) {
        List<Address> addressList = findAddressesByStudentId(studentId);

        List<@Valid AddressCreationDTO> addressDTOList = listTransfer.getAddressDTOList();
        addressList.forEach(address -> {
            address.setCity(addressDTOList.get(addressList.indexOf(address)).getCity());
            address.setStreet(addressDTOList.get(addressList.indexOf(address)).getStreet());
            address.setHouseNr(addressDTOList.get(addressList.indexOf(address)).getHouseNr());
            address.setPostalCode(addressDTOList.get(addressList.indexOf(address)).getPostalCode());
            addressRepository.save(address);
        });
    }

    private void initAddressTypesOnAddressList(List<Address> addressList) {
        addressList.get(0).setType(AddressType.PERMANENT);
        addressList.get(1).setType(AddressType.RESIDENTIAL);
        addressList.get(2).setType(AddressType.CORRESPONDENCE);
    }

    public Page<Address> listAddressesByStudentID(int id, int pageNr, String sortBy, String sortDirection) {
        Pageable pageable = utils.listAllElements(pageNr, sortBy, sortDirection);
        return addressRepository.findAllByStudentId(id, pageable);
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

    public void deleteAddressById(int id) {
        addressRepository.deleteAddressById(id);
    }

    public void updateCity(int id, String city) {
        addressRepository.updateCity(id, city);
    }
}
