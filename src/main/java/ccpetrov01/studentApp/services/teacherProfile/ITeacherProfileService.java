package ccpetrov01.studentApp.services.teacherProfile;

import ccpetrov01.studentApp.Dtos.TeacherProfileDtoView;
import ccpetrov01.studentApp.models.TeacherProfile;
import ccpetrov01.studentApp.projection.TeacherParticipation;
import ccpetrov01.studentApp.requests.UpdateTeacherProfileRequest;

import java.util.List;

public interface ITeacherProfileService {
    TeacherProfile addTeacherProfile(TeacherProfile teacherProfile);
    TeacherProfile getTeacherById(Integer teacherId);
    void deleteTeacherById(Integer teacherId);
    TeacherProfile updateTeacherProfile(UpdateTeacherProfileRequest profileRequest , Integer teacherId);
    List<TeacherParticipation> findClassParticipationByTeacherASC();
    Double findAvgExperienceBy();
    TeacherProfileDtoView toTeacherProfileDto(TeacherProfile teacherProfile);
    List<TeacherProfileDtoView> TeacherProfileDtoViewList(List<TeacherProfile> teacherProfileList);
}
