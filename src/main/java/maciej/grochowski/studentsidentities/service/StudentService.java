package maciej.grochowski.studentsidentities.service;

import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.AddressType;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private final StudentUtils utils = new StudentUtils();
    private final static Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public Student createStudentFromDTO(StudentCreationDTO DTO, List<Address> addressList) throws PeselDateNotMatchException, TooYoungException {
        utils.validatePesel(DTO);
        utils.validateAge(DTO);
        if (DTO.getMiddleName().isEmpty()) {
            return new Student(DTO.getFirstName(), DTO.getLastName(),
                    DTO.getPesel(), DTO.getDob(), addressList);
        } else {
            return new Student(DTO.getFirstName(), DTO.getMiddleName(), DTO.getLastName(),
                    DTO.getPesel(), DTO.getDob(), addressList);
        }
    }

    public StudentCreationDTO createDTOFromStudent(Student student) {
        List<Address> addressList = student.getAddressList();
        List<AddressCreationDTO> DTOList = new ArrayList<>();
        for (Address address : addressList) {
            AddressCreationDTO addressDTO = new AddressCreationDTO();
            addressDTO.setCity(address.getCity());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setHouseNr(address.getHouseNr());
            addressDTO.setPostalCode(address.getPostalCode());
            addressDTO.setType(address.getType());
            DTOList.add(addressDTO);
        }
            if (addressList.contains(addressList.get(0)))  addressList.get(0).setType(AddressType.PERMANENT);
            if (addressList.contains(addressList.get(1)))  addressList.get(1).setType(AddressType.RESIDENTIAL);
            if (addressList.contains(addressList.get(2))) addressList.get(2).setType(AddressType.CORRESPONDENCE);

        return new StudentCreationDTO(student.getFirstName(), student.getMiddleName(),
                student.getLastName(), student.getPesel(), student.getDob(), DTOList);
    }

    public void addStudent(Student student) {
        List<Address> addressList = student.getAddressList();
        addressList.forEach(address -> address.setStudent(student));
        studentRepository.save(student);
    }

    public void updateStudent(int id, Student student) {
        List<Address> addressList = student.getAddressList();
        addressList.forEach(address -> address.setStudent(student));
        studentRepository.save(student);
    }

    public List<Student> getAllStudentsCriteria() {
        return studentRepository.getAllStudentsCriteria();
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

