package maciej.grochowski.studentsidentities.controller;

import maciej.grochowski.studentsidentities.address.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    public StudentRepository studentRepository;

    @GetMapping("/addresses")
    public List<Address> findAllAddresses() {

        return addressRepository.findAll();
    }

    @GetMapping("/addresses/{addressId}")
    public Optional<Address> findAddressById(@PathVariable int addressId) {

        return addressRepository.findById(addressId);
    }

    @GetMapping("/addresses/{city}")
    public List<Address> getAddressByCity(@PathVariable String city) {
        return addressRepository.getAddressByCity(city);
    }

    @GetMapping("/students/{studentId}")
    public Optional<Student> findStudentById(@PathVariable int studentId) {
        return studentRepository.findById(studentId);
    }

    @GetMapping("/students/{id}/addresses")
    public List<Address> findAddressesByStudentId(@PathVariable int id) {

        return addressRepository.findAddressesByStudentId(id);
    }
}
