package maciej.grochowski.studentsidentities.controller;

import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SortingController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AddressService addressService;

    private final StudentUtils studentUtils = new StudentUtils();

    private static final Logger LOGGER = LoggerFactory.getLogger(SortingController.class);

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
