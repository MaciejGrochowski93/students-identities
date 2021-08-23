package maciej.grochowski.studentsidentities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreationDTO {

    public AddressCreationDTO(String street, String houseNr, String city, String postalCode) {
        this.street = street;
        this.houseNr = houseNr;
        this.city = city;
        this.postalCode = postalCode;
    }

    @NotBlank(message = "Please, provide the name of your street.")
    @Length(max = 50, message = "Street name must consist of 1 to 50 letters.")
    private String street;

    @NotBlank(message = "Please, provide the name of your street.")
    @Length(max = 50, message = "Street name must consist of 1 to 50 letters.")
    private String houseNr;

    @NotBlank(message = "Please, provide the name of your city.")
    @Length(max = 30, message = "City name must consist of 1 to 30 letters")
    private String city;

    @NotBlank(message = "Please, provide your postal code.")
    @Length(min = 6, max = 6, message = "Postal code must consist of 6 letters")
    private String postalCode;

    private AddressType type;
}
