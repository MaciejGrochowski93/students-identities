package maciej.grochowski.studentsidentities.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class StudentSortCriteria {

    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "lastName";
}
