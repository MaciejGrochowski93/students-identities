package maciej.grochowski.studentsidentities.address;

import lombok.*;
import maciej.grochowski.studentsidentities.entity.Student;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    public Address(String street, String houseNr, String city, String postalCode, AddressType type) {
        this.street = street;
        this.houseNr = houseNr;
        this.city = city;
        this.postalCode = postalCode;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @NotNull
    @Enumerated(EnumType.STRING)
    private AddressType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    public Student student;
}
