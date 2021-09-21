package maciej.grochowski.studentsidentities.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import maciej.grochowski.studentsidentities.DTO.AddressType;

import javax.persistence.*;

@Entity
@Data
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
}
