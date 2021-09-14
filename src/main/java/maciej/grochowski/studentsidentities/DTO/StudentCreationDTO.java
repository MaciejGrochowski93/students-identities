package maciej.grochowski.studentsidentities.DTO;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreationDTO {

    @NotBlank(message = "")
    @Length(min = 1, max = 20, message = "Your first name must consist of 1 to 20 letters.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "")
    @Length(min = 1, max = 30, message = "Your last name must consist of 1 to 30 letters.")
    private String lastName;

    @NotBlank(message = "")
    @Length(min = 11, max = 11, message = "Your pesel must consist of 11 letters.")
    private String pesel;

    @Past(message = "Hey, did you really come from the future?")
    @NotNull(message = "Please, provide date of your birth.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private List<@Valid AddressCreationDTO> addressDTOList;
}
