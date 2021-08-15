//package maciej.grochowski.studentsidentities.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import maciej.grochowski.studentsidentities.DTO.CorrespondenceAddress;
//import maciej.grochowski.studentsidentities.DTO.PermanentAddress;
//import maciej.grochowski.studentsidentities.DTO.ResidentialAddress;
//import maciej.grochowski.studentsidentities.entity.Student;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Address {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int addressId;
//
//    public Address(ResidentialAddress residentialAddress) {
//        this.residentialAddress = residentialAddress;
//    }
//
//    public Address(ResidentialAddress residentialAddress, PermanentAddress permanentAddress) {
//        this.residentialAddress = residentialAddress;
//        this.permanentAddress = permanentAddress;
//    }
//
//    private ResidentialAddress residentialAddress;
//    private PermanentAddress permanentAddress;
//    private CorrespondenceAddress correspondenceAddress;
//
//    @OneToOne
//    @JoinColumn(name = "student_id")
//    private Student student;
//}
