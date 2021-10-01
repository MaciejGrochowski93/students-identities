package maciej.grochowski.studentsidentities.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.entity.Address;
import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import maciej.grochowski.studentsidentities.service.AddressService;
import maciej.grochowski.studentsidentities.service.ModelService;
import maciej.grochowski.studentsidentities.service.StudentService;
import maciej.grochowski.studentsidentities.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AddressService addressService;
    private final ModelService modelService;
    private final Utils utils;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @ApiOperation(value = "Displays all Students from the database.")
    @GetMapping
    public String getStudentsPage(Model model) {
        return studentsListByPage(model, 1, "firstName", "asc");
    }

    @ApiOperation(value = "Displays all Students from the database, and allows you to sort them by parameters.")
    @GetMapping("/studentpage/{currentPage}")
    public String studentsListByPage(Model model,
                                     @PathVariable("currentPage") int pageNr,
                                     @Param("sortBy") String sortBy,
                                     @Param("sortDirection") String sortDirection) {

        Page<Student> studentsPage = studentService.listAllStudents(pageNr, sortBy, sortDirection);
        modelService.addSortingAttributesToModel(model, pageNr, sortBy, sortDirection);
        modelService.addPagingAttributesToModel(model, studentsPage);
        return "index";
    }

    @ApiOperation(value = "Allows you to create new Student, and it's Addresses.")
    @GetMapping("/addStudent")
    public String addStudentForm(Model model) {

        AddressListTransfer addressListTransfer = addressService.initListTransfer();
        model.addAttribute("addressListForm", addressListTransfer);
        model.addAttribute("studentDTOForm", new StudentCreationDTO());
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

            studentService.addStudent(studentDTOForm, addressTransfer);
        try {
        }
        catch (TooYoungException youthExc) {
            model.addAttribute("youthExc", youthExc.getMessage());
            LOGGER.error(youthExc.getMessage());
        }
        catch (PeselDateNotMatchException peselDateExc) {
            model.addAttribute("peselDateExc", peselDateExc.getMessage());
            LOGGER.error(peselDateExc.getMessage());
        }

        return "redirect:/student";
    }

    @ApiOperation(value = "Allows you to update Student's identities.")
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
        } catch (PeselDateNotMatchException peselDateExc) {
            model.addAttribute("peselDateExc", peselDateExc.getMessage());
            LOGGER.error(peselDateExc.getMessage());
        }

        return "redirect:/student";
    }

    @ApiOperation(value = "Removes Student, and his Addresses from the database.")
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudentById(@PathVariable int id) {
        studentService.deleteStudentById(id);
        return "redirect:/student";
    }

    @ApiOperation(value = "Displays amount of Students by the given age.")
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

    @ApiOperation(value = "Shows Addresses of a particular Student by it's ID.")
    @GetMapping("/addresses/{id}")
    public String getAddressesPage(Model model, @PathVariable int id) {
        return addressListByPage(model, id, 1, "city", "asc");
    }

    @ApiOperation(value = "Shows Addresses of a particular Student by it's ID, and allows you to sort them by parameters.")
    @GetMapping("/addresses/{id}/addressPage/{currentPage}")
    public String addressListByPage(Model model,
                                    @PathVariable("id") int id,
                                    @PathVariable("currentPage") int pageNr,
                                    @Param("sortBy") String sortBy,
                                    @Param("sortDirection") String sortDirection) {

        Page<Address> addressPage = addressService.listAddressesByStudentID(id, pageNr, sortBy, sortDirection);
        modelService.addSortingAttributesToModel(model, pageNr, sortBy, sortDirection);
        modelService.addPagingAttributesToModel(model, addressPage);
        modelService.addStudentsNameToModel(model, id);

        return "student_addresses";
    }

    @ApiOperation(value = "Allows you to update Student's Addresses.")
    @GetMapping("/addresses/{id}/update")
    public String updateAddress(@PathVariable int id, Model model) {
        AddressListTransfer addressListTransfer = addressService.createListTransferFromStudent(id);
        model.addAttribute("addressListTransfer", addressListTransfer);
        modelService.addStudentsNameToModel(model, id);
        return "update_address";
    }

    @PostMapping("/addresses/{id}/update")
    public String updateAddress(@ModelAttribute("addressListTransfer") @Valid AddressListTransfer listTransfer,
                                BindingResult addressResult, @PathVariable int id) {
        if (addressResult.hasErrors()) {
            return "update_address";
        }
        addressService.updateAddressesOfStudentId(id, listTransfer);
        return "redirect:/student";
    }

    @GetMapping("/previousPage")
    public String returnToPreviousPage(HttpServletRequest request) {
        return utils.getPreviousPageByRequest(request).orElse("/");
    }
}