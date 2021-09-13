package maciej.grochowski.studentsidentities.controller;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.model.StudentPage;
import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

    @GetMapping
    public String getStudentsPage(Model model) {
        return listByPage(model, 1, "firstName", "asc");
    }

    @GetMapping("/studentpage/{pageNr}")
    public String listByPage(Model model,
                             @PathVariable ("pageNr") int pageNr,
                             @Param("sortBy") String sortBy,
                             @Param("sortDirection") String sortDirection) {
        Page<Student> studentsPage = studentService.listAll(pageNr, sortBy, sortDirection);
        List<Student> studentsList = studentsPage.getContent();

        int totalPages = studentsPage.getTotalPages();
        long totalElements = studentsPage.getTotalElements();
        String reverseDirection = sortDirection.equals("asc") ? "desc" : "asc";

        model.addAttribute("studentsListPage", studentsList);
        model.addAttribute("currentPage", pageNr);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", reverseDirection);
        return "index";
    }

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
        return "redirect:/student";
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
                                BindingResult studentResult, @PathVariable int id, Model model) {
        if (studentResult.hasErrors()) {
            return "update_student";
        }

        try {
            studentService.updateStudent(id, studentDTO);
        } catch (TooYoungException youthExc) {
            model.addAttribute("youthExc", youthExc.getMessage());
            LOGGER.error(youthExc.getMessage());
            return "update_student";
        } catch (PeselDateNotMatchException peselDateExc) {
            model.addAttribute("peselDateExc", peselDateExc.getMessage());
            LOGGER.error(peselDateExc.getMessage());
            return "update_student";
        }

        return "redirect:/student";
    }

    @GetMapping("/addresses/{id}/update")
    public String updateAddress(@PathVariable int id, Model model) {
        AddressListTransfer addressListTransfer = addressService.createListTransferFromStudent(id);

        Student studentById = studentService.getStudentById(id);
        String studentName = String.format(studentById.getFirstName(), " ", studentById.getLastName());

        model.addAttribute("studentName", studentName);
        model.addAttribute("addressListTransfer", addressListTransfer);
        return "update_address";
    }

    @PostMapping("/addresses/{id}/update")
    public String updateAddress(@ModelAttribute("addressListTransfer") @Valid AddressListTransfer listTransfer,
                                BindingResult addressResult, Model model, @PathVariable int id) {
        if (addressResult.hasErrors()) {
            return "update_address";
        }
        addressService.updateAddressesOfStudentId(id, listTransfer);
        return "redirect:/student";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudentById(@PathVariable int id) {
        studentService.deleteStudentById(id);
        return "redirect:/student";
    }

    @GetMapping("/students/ageCount")
    public String countStudentsOfAge(@RequestParam("studentsOfAge") int age) {
        Long amountStudentsOfAge = studentService.countStudentsOfAge(age);
        if (amountStudentsOfAge > 0) {
            LOGGER.info("We found {} student(s) of age {}.", amountStudentsOfAge, age);
        } else {
            LOGGER.warn("We haven't found any students of age {}.", age);
        }
        return "redirect:/student";
    }
}