package maciej.grochowski.studentsidentities.service;

import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
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

    public List<AddressCreationDTO> create3xAddressDTO() {
        List<AddressCreationDTO> listDTO = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listDTO.add(new AddressCreationDTO());
        }
        return listDTO;
    }

    public void addStudent(Student student) {
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

