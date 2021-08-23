package maciej.grochowski.studentsidentities.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressListTransfer {

    private List<@Valid AddressCreationDTO> addressDTOList;

    public List<AddressCreationDTO> getAddressDTOList() {
        if (addressDTOList == null) {
            addressDTOList = new ArrayList<>();
        }
        return addressDTOList;
    }
}
