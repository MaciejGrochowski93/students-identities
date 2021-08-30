package maciej.grochowski.studentsidentities.entity;

import lombok.*;
import maciej.grochowski.studentsidentities.DTO.AddressType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    public Address(String street, String houseNr, String city, String postalCode, AddressType type) {
        this.street = street;
        this.houseNr = houseNr;
        this.city = city;
        this.postalCode = postalCode;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String street;

    private String houseNr;

    private String city;

    private String postalCode;

    @Enumerated(EnumType.STRING)
    private AddressType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    public Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(getId(), address.getId()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getHouseNr(), address.getHouseNr()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(getPostalCode(), address.getPostalCode()) && getType() == address.getType() && Objects.equals(getStudent(), address.getStudent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStreet(), getHouseNr(), getCity(), getPostalCode(), getType(), getStudent());
    }
}
