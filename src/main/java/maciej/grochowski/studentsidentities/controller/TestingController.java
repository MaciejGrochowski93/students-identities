package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.repository.AddressRepository;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// THIS CONTROLLER CONTAINS METHODS FOR TESTING PURPOSES ONLY - WILL BE REMOVED ONCE I FINISH THE PROJECT

@RestController
@AllArgsConstructor
public class TestingController {

    public final AddressRepository addressRepository;
    public final StudentRepository studentRepository;

    public static final Logger LOGGER = LoggerFactory.getLogger(TestingController.class);

    @PostMapping("/updateStudentFirstName/{id}/{firstName}")
    public void updateStudentFirstName(@PathVariable int id, @PathVariable String firstName) {
        studentRepository.updateFirstName(id, firstName);
    }
}