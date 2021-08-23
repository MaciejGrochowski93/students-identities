package maciej.grochowski.studentsidentities.service;

import maciej.grochowski.studentsidentities.DTO.AddressType;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
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

    public Student createStudentFromDTO(StudentCreationDTO DTO, List<Address> addressList) {
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
        listDTO.get(0).setType(AddressType.PERMANENT);
        listDTO.get(1).setType(AddressType.RESIDENTIAL);
        listDTO.get(2).setType(AddressType.CORRESPONDENCE);
        return listDTO;
    }

    public void addStudent(Student student) {
        if (utils.isPeselValid(student) && utils.isAdult(student)) {
            studentRepository.save(student);
        } else if (!utils.isPeselValid(student)) {
            LOGGER.error("Pesel and date of birth do not match.");
            throw new PeselDateNotMatchException("Pesel and date of birth do not match.");
        } if (!utils.isAdult(student)) {
            LOGGER.error("Student is too young.");
            throw new TooYoungException("Student is too young.");
        }
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
}

