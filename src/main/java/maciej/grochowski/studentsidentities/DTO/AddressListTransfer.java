package maciej.grochowski.studentsidentities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressListTransfer {

    private List<@Valid AddressCreationDTO> addressDTOList;

    public List<AddressCreationDTO> initListOfThreeAddressDTO() {
        if (addressDTOList == null) {
            addressDTOList = new ArrayList<>();
            for (int i = 0; i < AddressType.values().length; i++) {
                addressDTOList.add(new AddressCreationDTO());
            }
        }
        return addressDTOList;
    }
}
