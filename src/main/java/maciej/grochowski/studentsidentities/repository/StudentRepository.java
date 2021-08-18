package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>, StudentCustomRepository{
}
