package maciej.grochowski.studentsidentities.controller;

import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CriteriaController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AddressService addressService;

    private final StudentUtils studentUtils = new StudentUtils();

    private static final Logger LOGGER = LoggerFactory.getLogger(CriteriaController.class);

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

    @GetMapping("/sortByCity")
    public String sortByCity(HttpServletRequest request) {
        addressService.sortByCity();
        return studentUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/sortByStreet")
    public String sortByStreet(HttpServletRequest request) {
        addressService.sortByStreet();
        return studentUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/sortByHouseNr")
    public String sortByHouseNr(HttpServletRequest request) {
        addressService.sortByHouseNr();
        return studentUtils.getPreviousPageByRequest(request).orElse("/");
    }

    @GetMapping("/sortByPostalCode")
    public String sortByPostalCode(HttpServletRequest request) {
        addressService.sortByPostalCode();
        return studentUtils.getPreviousPageByRequest(request).orElse("/");
    }
}
