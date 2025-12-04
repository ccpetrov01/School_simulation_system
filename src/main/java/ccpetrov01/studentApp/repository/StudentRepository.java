package ccpetrov01.studentApp.repository;

import ccpetrov01.studentApp.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student , Integer> {
    List<Student> findByFirstname(String firstname);
    List<Student> findByFirstnameAndLastname(String firstname, String lastname);
    boolean existsByPhoneNumber(String phoneNumber);
}
