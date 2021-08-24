package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AddressService addressService;

    private StudentUtils studentUtils = new StudentUtils();

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/{id}/addresses")
    public String findAddressesByStudentId(@PathVariable int id, Model model) {
        Student studentById = studentService.getStudentById(id);
        String studentName = studentById.getFirstName() + " " + studentById.getLastName();
        List<Address> addressList = addressService.findAddressesByStudentId(id);
        model.addAttribute("studentName", studentName);
        model.addAttribute("addressList", addressList);
        return "student_addresses";
    }

    @GetMapping("/deleteAddress/{id}")
    public String deleteAddressById(@PathVariable int id, HttpServletRequest request) {
        addressService.deleteAddressById(id);
        return studentUtils.getPreviousPageByRequest(request).orElse("redirect:/");
    }
}