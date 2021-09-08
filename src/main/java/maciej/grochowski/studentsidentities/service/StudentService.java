package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AddressService addressService;
    private final StudentUtils utils;

    public void addStudent(StudentCreationDTO studentDTOForm, AddressListTransfer addressTransfer) {
        Student student = createStudentFromDTO(studentDTOForm, addressTransfer);

        List<Address> addressList = student.getAddressList();
        addressList.forEach(address -> address.setStudent(student));

        studentRepository.save(student);
    }

    public Student createStudentFromDTO(StudentCreationDTO DTO, AddressListTransfer addressTransfer) {
        utils.validatePesel(DTO);
        utils.validateAge(DTO);

        List<Address> addressList = addressService.createAddressListFromTransfer(addressTransfer);
        if (DTO.getMiddleName().isEmpty()) {
            return new Student(DTO.getFirstName(), DTO.getLastName(), DTO.getPesel(), DTO.getDob(), addressList);
        }
        return new Student(DTO.getFirstName(), DTO.getMiddleName(), DTO.getLastName(), DTO.getPesel(), DTO.getDob(), addressList);
    }

    public StudentCreationDTO createDTOFromStudentId(int id) {
        Student student = getStudentById(id);

        List<Address> addressList = student.getAddressList();
        List<AddressCreationDTO> DTOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressCreationDTO addressDTO = new AddressCreationDTO();
            addressDTO.setId(address.getId());
            addressDTO.setCity(address.getCity());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setHouseNr(address.getHouseNr());
            addressDTO.setPostalCode(address.getPostalCode());
            addressDTO.setType(address.getType());
            DTOList.add(addressDTO);
        }
        return new StudentCreationDTO(student.getFirstName(), student.getMiddleName(),
                student.getLastName(), student.getPesel(), student.getDob(), DTOList);
    }

    @Transactional
    public void updateStudent(int id, StudentCreationDTO DTO) {
        utils.validatePesel(DTO);
        utils.validateAge(DTO);

        Student student = getStudentById(id);
        student.setFirstName(DTO.getFirstName());
        student.setMiddleName(DTO.getMiddleName());
        student.setLastName(DTO.getLastName());
        student.setDob(DTO.getDob());
        student.setPesel(DTO.getPesel());

        studentRepository.save(student);
    }

    public List<Student> getAllStudentsCriteria() {
        return studentRepository.getAllStudentsByCriteria();
    }

    public void sortByFirstName() {
        studentRepository.sortByFirstName();
    }

    public void sortByMiddleName() {
        studentRepository.sortByMiddleName();
    }

    public void sortByLastName() {
        studentRepository.sortByLastName();
    }

    public void sortByAge() {
        studentRepository.sortByAge();
    }

    public Long countStudentsOfAge(int age) {
        return studentRepository.countStudentsOfAge(age);
    }

    public Student getStudentById(int id) {
        return studentRepository.getStudentByID(id);
    }

    public void deleteStudentById(int id) {
        studentRepository.deleteStudentById(id);
    }
}

