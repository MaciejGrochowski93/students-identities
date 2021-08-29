package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// THIS CONTROLLER CONTAINS METHODS FOR TESTING PURPOSES ONLY - WILL BE REMOVED ONCE I FINISH THE PROJECT

@AllArgsConstructor
@RestController
public class TestingController {

    public final StudentRepository studentRepository;

    @PostMapping("/updateStudentFirstName/{id}/{firstName}")
    public void updateStudentFirstName(@PathVariable int id, @PathVariable String firstName) {
        studentRepository.updateFirstName(id, firstName);
    }
}