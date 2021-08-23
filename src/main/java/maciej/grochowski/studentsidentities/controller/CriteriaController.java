package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Controller
public class CriteriaController {

    private final StudentService studentService;
    private final AddressService addressService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CriteriaController.class);

    @GetMapping("/students/ageCount")
    public String countStudentsOfAge(Model model, @RequestParam("studentsOfAge") @NotNull int age) {
        Long amountStudentsOfAge = studentService.countStudentsOfAge(age);
        if (amountStudentsOfAge > 0) {
            LOGGER.info("We found {} student(s) of age {}.", amountStudentsOfAge, age);
        } else {
            LOGGER.warn("We haven't found any students of age {}.", age);
        }
        return "redirect:/";
    }

    @GetMapping("/students/cityCount")
    public String countStudentsFromCity(Model model, @RequestParam("studentsFromCity") @NotNull String city) {
        Long amountStudentsFromCity = addressService.countStudentsFromCity(city);
        if (amountStudentsFromCity > 0) {
            LOGGER.info("We found {} student(s) from {}.", amountStudentsFromCity, city);
        } else {
            LOGGER.warn("We haven't found any students from {}.", city);
        }
        return "redirect:/";
    }

    @GetMapping("/sortByFirstName")
    public String sortByFirstName() {
        studentService.sortByFirstName();
        return "redirect:/";
    }

    @GetMapping("/sortByMiddleName")
    public String sortByMiddleName() {
        studentService.sortByMiddleName();
        return "redirect:/";
    }

    @GetMapping("/sortByLastName")
    public String sortByLastName() {
        studentService.sortByLastName();
        return "redirect:/";
    }

    @GetMapping("/sortByAge")
    public String sortByAge() {
        studentService.sortByAge();
        return "redirect:/";
    }
}
