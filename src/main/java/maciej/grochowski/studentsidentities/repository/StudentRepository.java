package maciej.grochowski.studentsidentities.repository;

import maciej.grochowski.studentsidentities.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer>, StudentCustomRepository, PagingAndSortingRepository<Student, Integer> {
}
