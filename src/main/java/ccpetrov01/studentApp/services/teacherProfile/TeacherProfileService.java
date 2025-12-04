package ccpetrov01.studentApp.services.teacherProfile;

import ccpetrov01.studentApp.Dtos.TeacherProfileDtoView;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.TeacherProfile;
import ccpetrov01.studentApp.projection.TeacherParticipation;
import ccpetrov01.studentApp.repository.TeacherProfileRepository;
import ccpetrov01.studentApp.requests.UpdateTeacherProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherProfileService implements ITeacherProfileService{
    private final TeacherProfileRepository teacherProfileRepository;

    @Override
    public TeacherProfile addTeacherProfile(TeacherProfile teacherProfile) {
        try {
            return teacherProfileRepository.save(teacherProfile);
        }catch(GlobalException e){
            throw new GlobalException("Something went wrong with adding new teacherProfile!" , e);
        }
    }

    @Override
    public TeacherProfile getTeacherById(Integer teacherId) {
        return teacherProfileRepository.findById(teacherId)
                .orElseThrow( ()-> new ResourceNotFoundException("Teacher with give id:" + teacherId + " doesn't exists!"));
    }

    @Override
    public void deleteTeacherById(Integer teacherId) {
        teacherProfileRepository.findById(teacherId).ifPresentOrElse(
                teacherProfileRepository::delete
        , ()->{
                    throw new ResourceNotFoundException("Teacher with give id:" + teacherId + " doesn't exists!");
                });
    }

    @Override
    public TeacherProfile updateTeacherProfile(UpdateTeacherProfileRequest profileRequest, Integer teacherId) {
        TeacherProfile teacherProfile =  teacherProfileRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id you entered" + teacherId + " is not found!"));

        teacherProfile.setExperience(profileRequest.getExperience());
        teacherProfile.setClasses_participated(profileRequest.getClasses_participated());
        teacherProfile.setPaid_vacation_days(profileRequest.getPaid_vacation_days());
        return teacherProfileRepository.save(teacherProfile);
    }

    @Override
    public List<TeacherParticipation> findClassParticipationByTeacherASC() {
        try{
           return teacherProfileRepository.findClassParticipationByTeacherASC();
        }catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("There are no teacher added already , try adding one!");
        }
    }

    @Override
    public Double findAvgExperienceBy() {
        try{
            return teacherProfileRepository.findAvgExperienceBy();
        }catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("There are no teacher added already , try adding one!");
        }
    }

    @Override
    public TeacherProfileDtoView toTeacherProfileDto(TeacherProfile teacherProfile) {
        TeacherProfileDtoView teacherProfileDtoView = new TeacherProfileDtoView();
        teacherProfileDtoView.setExperience(teacherProfile.getExperience());
        teacherProfileDtoView.setClasses_participated(teacherProfile.getClasses_participated());
        teacherProfileDtoView.setPaid_vacation_days(teacherProfile.getPaid_vacation_days());

        return teacherProfileDtoView;
    }

    @Override
    public List<TeacherProfileDtoView> TeacherProfileDtoViewList(List<TeacherProfile> teacherProfileList) {
        return teacherProfileList
                .stream()
                .map(this::toTeacherProfileDto)
                .toList();
    }


}
