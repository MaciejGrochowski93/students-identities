package maciej.grochowski.studentsidentities.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public abstract class AbstractAddress {

    @NotNull(message = "Please, provide the name of your street.")
    @Length(min = 1, max = 50, message = "Street name must consist of 1 to 50 letters.")
    private final String street;

    @NotNull(message = "Please, provide the name of your city.")
    @Length(min = 1, max = 30, message = "City name must consist of 1 to 30 letters")
    private final String city;

    @NotNull(message = "Please, provide your postal code.")
    @Length(min = 6, max = 6, message = "Postal code must consist of 6 letters")
    private final String postalCode;
}
