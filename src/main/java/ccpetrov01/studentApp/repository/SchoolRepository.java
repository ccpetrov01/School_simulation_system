package ccpetrov01.studentApp.repository;

import ccpetrov01.studentApp.models.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School , Integer> {
    @Query("SELECT sch.rating FROM School sch ORDER BY sch.rating ASC")
    List<Double> findAllRatingsOrderedAsc();

    School findByName(String Name);


}
