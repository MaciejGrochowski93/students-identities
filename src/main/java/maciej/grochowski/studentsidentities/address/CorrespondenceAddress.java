package maciej.grochowski.studentsidentities.address;

import lombok.Data;
import maciej.grochowski.studentsidentities.entity.Student;

import javax.persistence.Entity;

@Entity
@Data
//@Inheritance(strategy = InheritanceType.JOINED)
public class CorrespondenceAddress extends AbstractAddress {
    public CorrespondenceAddress() {
    }

    public CorrespondenceAddress(String street, String houseNr, String city, String postalCode, Student student) {
        super(street, houseNr, city, postalCode, student);
    }
}
