package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
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
    public String addStudentForm(Model model) {
        AddressListTransfer addressListTransfer = addressService.initListTransfer();

        model.addAttribute("studentDTOForm", new StudentCreationDTO());
        model.addAttribute("addressListForm", addressListTransfer);
        return "new_student";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentDTOForm") @Valid StudentCreationDTO studentDTOForm,
                             BindingResult studentResult,
                             @ModelAttribute("addressListForm") @Valid AddressListTransfer addressTransfer,
                             BindingResult addressResult, @Valid Model model) {

        if (studentResult.hasErrors() || addressResult.hasErrors()) {
            return "new_student";
        }
        try {
            studentService.addStudent(studentDTOForm, addressTransfer);
        } catch (TooYoungException youthExc) {
            model.addAttribute("youthExc", youthExc.getMessage());
            LOGGER.error(youthExc.getMessage());
            return "new_student";
        } catch (PeselDateNotMatchException peselDateExc) {
            model.addAttribute("peselDateExc", peselDateExc.getMessage());
            LOGGER.error(peselDateExc.getMessage());
            return "new_student";
        }
        return "redirect:/";
    }

    @GetMapping("/addresses/{id}")
    public String findAddressesByStudentId(@PathVariable int id, Model model) {
        Student studentById = studentService.getStudentById(id);
        String studentName = String.format(studentById.getFirstName(), " ", studentById.getLastName());
        List<Address> addressList = addressService.findAddressesByStudentId(id);
        model.addAttribute("studentName", studentName);
        model.addAttribute("addressList", addressList);
        return "student_addresses";
    }

    @GetMapping("/updateStudent/{id}")
    public String updateStudent(@PathVariable int id, Model model) {
        StudentCreationDTO createdDTO = studentService.createDTOFromStudentId(id);
        model.addAttribute("studentDTOForm", createdDTO);
        return "update_student";
    }

    @PostMapping("/updateStudent/{id}")
    public String updateStudent(@ModelAttribute("studentDTOForm") @Valid StudentCreationDTO studentDTO,
                                BindingResult studentResult, @PathVariable int id,
                                Model model) {
        if (studentResult.hasErrors()) {
            return "redirect:/student/updateStudent/{id}";
        }
        studentService.updateStudent(id, studentDTO);
        return "redirect:/";
    }

    @GetMapping("/addresses/{id}/update")
    public String updateAddress(@PathVariable int id, Model model) {
        List<Address> addressList = addressService.findAddressesByStudentId(id);
        AddressListTransfer listTransfer = new AddressListTransfer();


        Student studentById = studentService.getStudentById(id);
        String studentName = String.format(studentById.getFirstName(), " ", studentById.getLastName());
//        addressService.updateAddress();

        model.addAttribute("studentName", studentName);
        model.addAttribute("addressList", addressList);
        return "update_address";
    }
//
//    @PostMapping("/addresses/{id}/update")
//    public String updateAddress(@PathVariable int id, @ModelAttribute ("addressList") @Valid List<Address> addressList) {
//        Student studentById = studentService.getStudentById(id);
//        String studentName = String.format(studentById.getFirstName(), " ", studentById.getLastName());
//
//        model.addAttribute("studentName", studentName);
//        model.addAttribute("addressList", addressList);
//        return "update_address";
//    }

    //    // NOT WORKING YET
//    @GetMapping("/deleteStudent/{id}")
//    public String deleteStudentById(@PathVariable int id) {
//        studentService.deleteStudentById(id);
//        return "redirect:/";
//    }

//    @GetMapping("/deleteAddress/{id}")
//    public String deleteAddressById(@PathVariable int id, HttpServletRequest request) {
//        addressService.deleteAddressById(id);
//        return studentUtils.getPreviousPageByRequest(request).orElse("redirect:/");
//    }
}