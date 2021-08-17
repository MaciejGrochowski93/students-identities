package maciej.grochowski.studentsidentities.service;

import maciej.grochowski.studentsidentities.entity.Student;
import maciej.grochowski.studentsidentities.repository.StudentRepository2;
import maciej.grochowski.studentsidentities.utils.StudentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository2 studentRepository2;

    @Autowired
    private AddressService addressService;

    private final StudentUtils utils = new StudentUtils();

    public void addStudent(Student student) {
        if (utils.isPeselValid(student) && utils.isAdult(student)) {
            addressService.saveAddressesOfStudent(student);
            studentRepository2.save(student);
        }
    }

    public List<Student> getAllStudents() {
        return studentRepository2.findAll();
    }
}

//    public void setDefaultStudentAddresses(Student student) {
//        ResidentialAddress resAddress = student.getResidentialAddress();
//        CorrespondenceAddress corAddress = student.getCorrespondenceAddress();
//        PermanentAddress permAddress = student.getPermanentAddress();
//
//        if (corAddress == null) {
//            corAddress.setCity(resAddress.getCity());
//            corAddress.setStreet(resAddress.getStreet());
//            corAddress.setHouseNr(resAddress.getHouseNr());
//            corAddress.setPostalCode(resAddress.getPostalCode());
//        }
//
//        if (permAddress == null) {
//            permAddress.setCity(resAddress.getCity());
//            permAddress.setStreet(resAddress.getStreet());
//            permAddress.setHouseNr(resAddress.getHouseNr());
//            permAddress.setPostalCode(resAddress.getPostalCode());
//        }
//    }

//    public void addStudent2() {
//
//        ArrayList<Address> list = new ArrayList<>();
//        list.add(new Address("Lipniak", "24A", "Lublin", "20-050", AddressType.RESIDENTIAL));
//        Student student = new Student(1, "Maciej", null, "Grochowski", "93021712458", LocalDate.of(1993, 02, 17),
//                list);
//        if (utils.isPeselValid(student) && utils.isAdult(student)) {
//            studentRepository2.save(student);
//            addressService.saveAddressesOfStudent(student);
//        }
//    }

