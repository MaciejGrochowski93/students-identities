package maciej.grochowski.studentsidentities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressListTransfer {

    private List<@Valid AddressCreationDTO> addressDTOList;

    public List<AddressCreationDTO> initListOfThreeAddressDTO() {
        if (addressDTOList == null) {
            addressDTOList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                addressDTOList.add(new AddressCreationDTO());
            }
        }
        return addressDTOList;
    }
}
