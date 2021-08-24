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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/addStudent")
    public String addStudentForm(@Valid Model model) {
        AddressListTransfer addressListTransfer = new AddressListTransfer();
        List<AddressCreationDTO> addressDTOList = studentService.create3xAddressDTO();
        addressListTransfer.setAddressDTOList(addressDTOList);

        model.addAttribute("studentDTOForm", new StudentCreationDTO());
        model.addAttribute("addressListForm", addressListTransfer);
        return "new_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentDTOForm") @Valid StudentCreationDTO studentDTOForm,
                             BindingResult studentResult,
                             @ModelAttribute("addressListForm") @Valid AddressListTransfer addressTransfer,
                             BindingResult addressResult
    ) {
        if (studentResult.hasErrors() || addressResult.hasErrors()) {
            return "new_student";
        }
        List<AddressCreationDTO> addressDTOList = addressTransfer.getAddressDTOList();
        List<Address> addressList = addressService.createAddressListFromDTO(addressDTOList);
        Student createdStudent = studentService.createStudentFromDTO(studentDTOForm, addressList);

        try {
            addressService.saveAddressesOfStudent(createdStudent);
            studentService.addStudent(createdStudent);
        } catch (PeselDateNotMatchException peselException) {
            LOGGER.error(peselException.getMessage());
        } catch (TooYoungException ageException) {
            LOGGER.error(ageException.getMessage());
        }

        return "redirect:/";
    }

    // NOT WORKING YET
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudentById(@PathVariable int id) {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }
}
