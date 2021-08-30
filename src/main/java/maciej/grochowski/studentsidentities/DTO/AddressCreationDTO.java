package maciej.grochowski.studentsidentities.DTO;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreationDTO {

    public AddressCreationDTO(String street, String houseNr, String city, String postalCode) {
        this.street = street;
        this.houseNr = houseNr;
        this.city = city;
        this.postalCode = postalCode;
    }

    private Integer id;

    @NotBlank(message = "")
    @Length(min = 1, max = 50, message = "Street name must consist of 1 to 50 letters.")
    private String street;

    @NotBlank(message = "")
    @Length(min = 1, max = 30, message = "House nr must consist of 1 to 30 letters.")
    private String houseNr;

    @NotBlank(message = "")
    @Length(min = 1, max = 30, message = "City name must consist of 1 to 30 letters.")
    private String city;

    @NotBlank(message = "")
    @Length(min = 6, max = 6, message = "Postal code must consist of 6 letters.")
    private String postalCode;

    private AddressType type;
}
