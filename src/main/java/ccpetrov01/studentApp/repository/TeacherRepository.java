package ccpetrov01.studentApp.repository;

import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher , Integer> {

    List<Teacher> findByFirstname(String firstname);
    List<Teacher> findByFirstnameAndLastname(String firstname, String lastname);
    boolean existsByPhoneNumber(String phoneNumber);
}
