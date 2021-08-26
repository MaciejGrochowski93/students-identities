package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressCreationDTO;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final StudentService studentService;
    private final AddressService addressService;

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @GetMapping()
    public String index(Model model) {
        List<Student> allStudents = studentService.getAllStudentsCriteria();
        model.addAttribute("studentsList", allStudents);
        return "index";
    }

    @GetMapping("/students/ageCount")
    public String countStudentsOfAge(@RequestParam("studentsOfAge") int age) {
        Long amountStudentsOfAge = studentService.countStudentsOfAge(age);
        if (amountStudentsOfAge > 0) {
            LOGGER.info("We found {} student(s) of age {}.", amountStudentsOfAge, age);
        } else {
            LOGGER.warn("We haven't found any students of age {}.", age);
        }
        return "redirect:/";
    }

    // NOT WORKING YET
    @GetMapping("/students/cityCount")
    public String countStudentsFromCity(Model model, @RequestParam("studentsFromCity") String city) {
        Long amountStudentsFromCity = addressService.countStudentsFromCity(city);
        if (amountStudentsFromCity > 0) {
            LOGGER.info("We found {} student(s) from {}.", amountStudentsFromCity, city);
        } else {
            LOGGER.warn("We haven't found any students from {}.", city);
        }
        return "redirect:/";
    }
}
