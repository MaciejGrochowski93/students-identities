package maciej.grochowski.studentsidentities.address;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddressCreationDTO {

    private List<Address> addressList = new ArrayList<>();

    public void addAddress(Address address) {
        this.addressList.add(address);
    }
}
