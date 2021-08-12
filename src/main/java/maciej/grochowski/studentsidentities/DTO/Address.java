package maciej.grochowski.studentsidentities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    public Address(ResidentialAddress residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public Address(ResidentialAddress residentialAddress, PermanentAddress permanentAddress) {
        this.residentialAddress = residentialAddress;
        this.permanentAddress = permanentAddress;
    }

    private ResidentialAddress residentialAddress;
    private PermanentAddress permanentAddress;
    private CorrespondenceAddress correspondenceAddress;
}
