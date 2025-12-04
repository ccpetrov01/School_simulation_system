package ccpetrov01.studentApp.services.studentProfile;

import ccpetrov01.studentApp.Dtos.StudentProfileDtoView;
import ccpetrov01.studentApp.models.StudentProfile;
import ccpetrov01.studentApp.requests.UpdateStudentProfileRequest;

import java.util.List;

public interface IStudentProfileService {

    StudentProfile addStudentProfile(StudentProfile studentProfile);
    StudentProfile getStudentProfileById(Integer student_id);
    void deleteStudentProfileById(Integer student_id);
    StudentProfile updateStudentProfileById(UpdateStudentProfileRequest profileRequest , Integer student_id);
    Double findAverageGradesByStudent(Integer student_id , String subject);
    List<StudentProfile> findGradesBySubject(String subject , Integer student_id);

    StudentProfileDtoView toStudentProfileDto(StudentProfile studentProfile);
    List<StudentProfileDtoView> toStudentProfileDtoList(List<StudentProfile> studentProfileList);
}
