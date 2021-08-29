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

@AllArgsConstructor
@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AddressService addressService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @GetMapping("/addStudent")
    public String addStudentForm(@Valid Model model) {
        AddressListTransfer addressListTransfer = new AddressListTransfer();
        List<AddressCreationDTO> addressCreationDTOS = addressListTransfer.initListOfThreeAddressDTO();
        addressListTransfer.setAddressDTOList(addressCreationDTOS);

        model.addAttribute("studentDTOForm", new StudentCreationDTO());
        model.addAttribute("addressListForm", addressListTransfer);
        return "new_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentDTOForm") @Valid StudentCreationDTO studentDTOForm,
                             BindingResult studentResult,
                             @ModelAttribute("addressListForm") @Valid AddressListTransfer addressTransfer,
                             BindingResult addressResult, Model model) {
        if (studentResult.hasErrors() || addressResult.hasErrors()) {
            return "new_student";
        }

        List<Address> addressList = addressService.createAddressListFromTransfer(addressTransfer);

        try {
            Student createdStudent = studentService.createStudentFromDTO(studentDTOForm, addressList);
            studentService.addStudent(createdStudent);
        } catch (TooYoungException youthExc) {
            model.addAttribute("youthExc", youthExc.getMessage());
            LOGGER.error(youthExc.getMessage());
            return "new_student";
        } catch (PeselDateNotMatchException peselDateExc) {
            model.addAttribute("peselDateExc", peselDateExc.getMessage());
            LOGGER.error(peselDateExc.getMessage());
            return "new_student";
        }
        return "index";
    }

    @GetMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable int id, @Valid Model model) {
        Student student = studentService.getStudentById(id);
        StudentCreationDTO fetchedDTO = studentService.createDTOFromStudent(student);

        List<AddressCreationDTO> addressDTOList = fetchedDTO.getAddressDTOList();
        AddressListTransfer addressListTransfer = new AddressListTransfer();
        addressListTransfer.setAddressDTOList(addressDTOList);

        model.addAttribute("studentDTOForm", fetchedDTO);
        model.addAttribute("addressListForm", addressListTransfer);
        return "update_student";
    }

    @PostMapping("/updateStudent/{id}")
    public String updateStudent(@ModelAttribute("studentDTOForm") @Valid StudentCreationDTO studentDTO,
                                BindingResult studentResult, @PathVariable int id,
                                @ModelAttribute("addressListForm") @Valid AddressListTransfer addressListTransfer,
                                BindingResult addressResult, Model model) {
        if (studentResult.hasErrors() || addressResult.hasErrors()) {
            return "update_student";
        }

        List<Address> addressList = addressService.createAddressListFromTransfer(addressListTransfer);

        try {
            Student updatedStudent = studentService.createStudentFromDTO(studentDTO, addressList);
            studentService.updateStudent(id, updatedStudent);
        } catch (TooYoungException youthExc) {
            model.addAttribute("youthExc", youthExc.getMessage());
            LOGGER.error(youthExc.getMessage());
            return "update_student";
        } catch (PeselDateNotMatchException peselDateExc) {
            model.addAttribute("peselDateExc", peselDateExc.getMessage());
            LOGGER.error(peselDateExc.getMessage());
            return "update_student";
        }
        return "redirect:/";
    }

    // NOT WORKING YET
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudentById(@PathVariable int id) {
        studentService.deleteStudentById(id);
        return "index";
    }

    @GetMapping("/addresses/{id}")
    public String findAddressesByStudentId(@PathVariable int id, Model model) {
        Student studentById = studentService.getStudentById(id);
        String studentName = studentById.getFirstName() + " " + studentById.getLastName();
        List<Address> addressList = addressService.findAddressesByStudentId(id);
        model.addAttribute("studentName", studentName);
        model.addAttribute("addressList", addressList);
        return "student_addresses";
    }

//    @GetMapping("/deleteAddress/{id}")
//    public String deleteAddressById(@PathVariable int id, HttpServletRequest request) {
//        addressService.deleteAddressById(id);
//        return studentUtils.getPreviousPageByRequest(request).orElse("redirect:/");
//    }
}