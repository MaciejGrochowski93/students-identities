package maciej.grochowski.studentsidentities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class StudentsIdentitiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsIdentitiesApplication.class, args);
        String ssgdfs = "123456789";
        System.out.println(ssgdfs.substring(0, 5));
    }
}
