package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.AddressType;
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
    private final StudentUtils utils;

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

    public void addStudent(Student student) {
        List<Address> addressList = student.getAddressList();
        addressList.forEach(address -> address.setStudent(student));
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Student existing, Student updated) {
        existing.setFirstName(updated.getFirstName());
        existing.setMiddleName(updated.getMiddleName());
        existing.setLastName(updated.getLastName());
        existing.setDob(updated.getDob());
        existing.setPesel(updated.getPesel());
        existing.setAddressList(updated.getAddressList());
        List<Address> addressList = existing.getAddressList();
        addressList.forEach(address -> address.setStudent(existing));
        studentRepository.save(existing);
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

