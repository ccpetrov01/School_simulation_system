package ccpetrov01.studentApp.services.school;

import ccpetrov01.studentApp.Dtos.SchoolDtoView;
import ccpetrov01.studentApp.Dtos.StudentDtoView;
import ccpetrov01.studentApp.models.School;
import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.requests.UpdateSchoolRequest;
import ccpetrov01.studentApp.requests.UpdateStudentRequests;

import java.util.List;

public interface ISchoolService {

    School addSchool(School school);
    School getSchoolById(Integer schoolId);
    void deleteSchoolById(Integer schoolId);
    School updateSchoolInformation(UpdateSchoolRequest request, Integer schoolId);
    List<School> getAllSchools();
    List<Double> findAllRatingsOrderedAsc();
    School findByName(String Name);
    SchoolDtoView schoolDtoView(School school);
    List<SchoolDtoView> schoolDtoViewList(List<School> schoolList);
}
