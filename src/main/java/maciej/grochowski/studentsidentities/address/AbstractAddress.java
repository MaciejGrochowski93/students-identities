package maciej.grochowski.studentsidentities.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.entity.Student;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractAddress {

    public AbstractAddress(String street, String houseNr, String city, String postalCode, Student student) {
        this.street = street;
        this.houseNr = houseNr;
        this.city = city;
        this.postalCode = postalCode;
        this.student = student;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Please, provide the name of your street.")
    @Length(min = 1, max = 50, message = "Street name must consist of 1 to 50 letters.")
    private String street;

    @NotNull(message = "Please, provide the name of your street.")
    @Length(min = 1, max = 50, message = "Street name must consist of 1 to 50 letters.")
    private String houseNr;

    @NotNull(message = "Please, provide the name of your city.")
    @Length(min = 1, max = 30, message = "City name must consist of 1 to 30 letters")
    private String city;

    @NotNull(message = "Please, provide your postal code.")
    @Length(min = 6, max = 6, message = "Postal code must consist of 6 letters")
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
