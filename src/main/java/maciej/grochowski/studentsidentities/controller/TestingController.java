package maciej.grochowski.studentsidentities.controller;

import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestingController {

    public static final Logger STDOUT = LoggerFactory.getLogger(TestingController.class);

    @Autowired
    public AddressRepository addressRepository;
    @Autowired
    public StudentRepository studentRepository;

    @GetMapping("/addresses/students/{city}")
    public List<Address> getAddressByCity(@PathVariable String city) {
        List<Address> addressByCity = addressRepository.getAddressByCity(city);
        STDOUT.info(String.valueOf(addressByCity));
        return addressByCity;
    }

    @GetMapping("/getStudentById/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentRepository.getAddressById(id);
    }

    @PostMapping("/updateStudentFirstName/{id}/{firstName}")
    public void updateStudentFirstName(@PathVariable int id, @PathVariable String firstName) {
        studentRepository.updateFirstName(id, firstName);
    }

    @DeleteMapping("/deleteStudentById/{id}")
    public void deleteStudentById(@PathVariable int id) {
        studentRepository.deleteStudentById(id);
    }

    @GetMapping("/addressById/{id}")
    public Address getAddressById(@PathVariable int id) {
        return addressRepository.getAddressById(id);
    }

    @PostMapping("/updateAddressCity/{id}/{city}")
    public void updateAddressCity(@PathVariable int id, @PathVariable String city) {
        addressRepository.updateCity(id, city);
    }

    @DeleteMapping("/deleteAddressById/{id}")
    public void deleteAddressById(@PathVariable int id) {
        addressRepository.deleteAddressById(id);
    }

    @GetMapping("/sortedByName")
    public void getSortedAddresses() {
        studentRepository.sortByFirstName();
    }

    @GetMapping("/countStudentsFromCity/{city}")
    public List<Student> countStudentsFromCity(@PathVariable String city) {
        return studentRepository.getStudentsFromCity2222(city);
    }

    @GetMapping("/countStudentsFromCity2/{city}")
    public List<Student> countStudentsFromCity2(@PathVariable String city) {
        return studentRepository.getStudentsFromCity3333(city);
    }

    @GetMapping("/addresses/cities/{city}")
    public Long getStudentsCountByCity(@PathVariable String city) {
        Long studentsCountByCity = addressRepository.countStudentsFromCity(city);
        STDOUT.info(String.valueOf(studentsCountByCity));
        return studentsCountByCity;
    }

    @GetMapping("/students/{id}/addresses")
    public List<Address> findAddressesByStudentId(@PathVariable int id) {
        return addressRepository.findAddressesByStudentId(id);
    }
}