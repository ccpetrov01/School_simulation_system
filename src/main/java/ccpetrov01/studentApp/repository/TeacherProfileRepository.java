package ccpetrov01.studentApp.repository;

import ccpetrov01.studentApp.models.TeacherProfile;
import ccpetrov01.studentApp.projection.TeacherParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile , Integer > {

    Double findAvgExperienceBy();

    @Query("SELECT tp.teacher.firstname AS tfirstname, " +
            "tp.teacher.lastname AS tlastname, " +
            "tp.classes_participated AS classesParticipated " +
            "FROM TeacherProfile tp " +
            "ORDER BY tp.classes_participated ASC")
    List<TeacherParticipation> findClassParticipationByTeacherASC();

}
