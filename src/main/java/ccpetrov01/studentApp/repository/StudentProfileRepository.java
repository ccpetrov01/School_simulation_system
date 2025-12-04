package ccpetrov01.studentApp.repository;

import ccpetrov01.studentApp.models.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentProfileRepository extends JpaRepository<StudentProfile , Integer> {
    List<StudentProfile> findByStudent_IdAndSubjects(Integer studentId, String subject);

    @Query("SELECT AVG(sp.grade) FROM StudentProfile sp " +
            "WHERE sp.student.id = :studentId AND sp.subjects = :subject")
    Double findAverageGradeForStudentAndSubject(@Param("studentId") Integer studentId,
                                                @Param("subject") String subject);

}
