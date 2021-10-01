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
