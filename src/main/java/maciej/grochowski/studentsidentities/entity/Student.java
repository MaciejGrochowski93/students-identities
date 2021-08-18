package maciej.grochowski.studentsidentities.entity;

import lombok.*;
import maciej.grochowski.studentsidentities.address.Address;
import maciej.grochowski.studentsidentities.address.AddressType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    public Student(String firstName, String middleName, String lastName, String pesel, LocalDate dob, List<Address> addressList) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dob = dob;
        this.addressList = addressList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
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
    @Length(min = 11, max = 11, message = "Your pesel must consist of 11 letters.")
    @Column(name = "pesel")
    private String pesel;

    @NotNull(message = "You have to provide your date of birth.")
    @Column(name = "date_of_birth")
    private LocalDate dob;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList;

    public List<Address> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

