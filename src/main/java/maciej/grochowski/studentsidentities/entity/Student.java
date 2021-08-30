package maciej.grochowski.studentsidentities.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
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

    private String firstName;

    private String middleName;

    private String lastName;

    private String pesel;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @JsonIgnore
    @OneToMany(mappedBy = "student",
            cascade = CascadeType.ALL
            , orphanRemoval = true
    )
    private List<Address> addressList;

    public List<Address> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        return addressList;
    }

    public void setAddressList(List<Address> list) {
        this.addressList.clear();
        this.addressList.addAll(list);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getFirstName(), student.getFirstName()) && getMiddleName().equals(student.getMiddleName()) && Objects.equals(getLastName(), student.getLastName()) && Objects.equals(getPesel(), student.getPesel()) && Objects.equals(getDob(), student.getDob()) && Objects.equals(getAddressList(), student.getAddressList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getMiddleName(), getLastName(), getPesel(), getDob(), getAddressList());
    }
}

