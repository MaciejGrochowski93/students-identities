package maciej.grochowski.studentsidentities.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.address.CorrespondenceAddress;
import maciej.grochowski.studentsidentities.address.PermanentAddress;
import maciej.grochowski.studentsidentities.address.ResidentialAddress;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAddress {

    private ResidentialAddress residentialAddress;
    private PermanentAddress permanentAddress;
    private CorrespondenceAddress correspondenceAddress;
}
