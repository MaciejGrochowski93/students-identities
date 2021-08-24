package maciej.grochowski.studentsidentities.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    public Student(String firstName, String lastName, String pesel, LocalDate dob, List<Address> addressList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dob = dob;
        this.addressList = addressList;
    }

    public Student(String firstName, String middleName, String lastName, String pesel, LocalDate dob, List<Address> addressList) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dob = dob;
        this.addressList = addressList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList;

    public List<Address> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

