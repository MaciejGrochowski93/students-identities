package maciej.grochowski.studentsidentities.controller;

import maciej.grochowski.studentsidentities.address.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestingController {

    public static final Logger STDOUT = LoggerFactory.getLogger(TestingController.class);

    @Autowired
    public AddressRepository addressRepository;
    @Autowired
    public StudentRepository studentRepository;

    @GetMapping("/students/age/{age}")
    public List<Student> getStudentsOfAge(@PathVariable int age) {
        return studentRepository.findStudentsOfAge(age);
    }

    @GetMapping("/addresses/students/{city}")
    public List<Address> getAddressByCity(@PathVariable String city) {
        List<Address> addressByCity = addressRepository.getAddressByCity(city);
        STDOUT.info(String.valueOf(addressByCity));
        return addressByCity;
    }

    @GetMapping("/sortedByName")
    public void getSortedAddresses() {
        studentRepository.sortByFirstName();
    }

    @GetMapping("/addresses/cities/{city}")
    public Long getStudentsCountByCity(@PathVariable String city) {
        Long studentsCountByCity = addressRepository.getStudentsCountByCity(city);
        STDOUT.info(String.valueOf(studentsCountByCity));
        return studentsCountByCity;
    }

    @GetMapping("/students/{id}/addresses")
    public List<Address> findAddressesByStudentId(@PathVariable int id) {
        return addressRepository.findAddressesByStudentId(id);
    }
}

//    @GetMapping("/responseEntity")
//    public ResponseEntity<Page<Student>> getAllStudents(StudentPage studentPage) {
//        return new ResponseEntity<>(studentService.getStudents(studentPage), HttpStatus.OK);
//    }