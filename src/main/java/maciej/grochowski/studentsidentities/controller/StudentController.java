package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.address.Address;
import maciej.grochowski.studentsidentities.address.AddressCreationDTO;
import maciej.grochowski.studentsidentities.address.AddressType;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    @GetMapping()
    public String index(Model model) {
        List<Student> allStudents = studentService.getAllStudents();
        model.addAttribute("studentsList", allStudents);
        return "index";
    }


    @GetMapping("/addStudent")
    public String addStudentForm(Model model) {
        AddressCreationDTO addressForm = new AddressCreationDTO();
        List<Address> addressList = addressForm.getAddressList();
        for (int i = 0; i < 3; i++) {
            addressList.add(new Address());
        }
        addressForm.setAddressList(addressList);

        model.addAttribute("addressForm", addressForm);
        model.addAttribute("studentForm", new Student());
        return "new_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentForm") @Valid Student student,
                             @ModelAttribute("addressForm") AddressCreationDTO form,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "new_student";
        }

        List<Address> addressList = form.getAddressList();
        addressList.get(0).setType(AddressType.PERMANENT);
        addressList.get(1).setType(AddressType.RESIDENTIAL);
        addressList.get(2).setType(AddressType.CORRESPONDENCE);

        student.setAddressList(addressList);
        addressService.saveAddressesOfStudent(student);
        studentService.addStudent(student);
        return "redirect:/";
    }
}