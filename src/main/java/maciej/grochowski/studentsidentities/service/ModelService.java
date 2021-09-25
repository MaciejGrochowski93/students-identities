package maciej.grochowski.studentsidentities.service;

import lombok.AllArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressListTransfer;
import maciej.grochowski.studentsidentities.DTO.StudentCreationDTO;
import maciej.grochowski.studentsidentities.controller.StudentController;
import maciej.grochowski.studentsidentities.exception.PeselDateNotMatchException;
import maciej.grochowski.studentsidentities.exception.TooYoungException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelService {

    private final StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);


    public String addStudentCreationAttributesToModel(StudentCreationDTO studentDTOForm,
                                                      AddressListTransfer addressTransfer, Model model) {
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

    public String addStudentUpdateAttributesToModel(StudentCreationDTO studentDTO, int studentId, Model model) {
        try {
            studentService.updateStudent(studentId, studentDTO);
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


    public void addSortingAttributesToModel(Model model, int pageNr, String sortBy, String sortDirection) {
        String reverseDirection = sortDirection.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNr);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseDirection", reverseDirection);
    }

    public void addPagingAttributesToModel(Model model, Page page) {
        List list = page.getContent();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        model.addAttribute("listPage", list);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
    }

    public void addStudentsNameToModel(Model model, int id) {
        String studentName = studentService.getStudentNames(id);
        model.addAttribute("studentName", studentName);
    }
}
