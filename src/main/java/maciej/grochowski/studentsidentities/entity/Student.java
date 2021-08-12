package maciej.grochowski.studentsidentities.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "You have to provide your first name.")
    @Length(min = 1, max = 20, message = "Your first name must consist of 1 to 20 letters.")
    @Column(name = "first_name")
    private String firstName;

    @Length(min = 1, max = 20, message = "Your middle name must consist of 1 to 20 letters.")
    @Column(name = "middle_name")
    private String middleName;

    @NotNull(message = "You have to provide your last name.")
    @Length(min = 1, max = 30, message = "Your last name must consist of 1 to 30 letters.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "You have to provide your pesel.")
    @Length(min = 11, max = 11)
    @Column(name = "pesel")
    private String pesel;

    @NotNull(message = "You have to provide your date of birth.")
    @Column(name = "date_of_birth")
    private LocalDate dob;
}
