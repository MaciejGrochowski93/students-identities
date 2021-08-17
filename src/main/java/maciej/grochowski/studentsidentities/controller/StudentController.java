package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.address.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final AddressService addressService;

    @GetMapping()
    public String index(Model model) {
        List<Student> allStudents = studentService.getAllStudents();
        model.addAttribute("studentsList", allStudents);
        return "index";
    }

    @GetMapping("/addStudent")
    public String addStudentForm(Model model) {
        model.addAttribute("studentForm", new Student());
        model.addAttribute("addressForm", new Address());
        return "new_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentForm") @Valid Student student,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "new_student";
        }
        studentService.addStudent(student);
        return "redirect:/";
    }
}