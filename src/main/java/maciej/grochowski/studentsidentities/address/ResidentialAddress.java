package maciej.grochowski.studentsidentities.address;

import lombok.Data;
import maciej.grochowski.studentsidentities.entity.Student;

import javax.persistence.Entity;

@Entity
@Data
//@Inheritance(strategy = InheritanceType.JOINED)
public class ResidentialAddress extends AbstractAddress {
    public ResidentialAddress() {
    }

    public ResidentialAddress(String street, String houseNr, String city, String postalCode, Student student) {
        super(street, houseNr, city, postalCode, student);
    }
}
