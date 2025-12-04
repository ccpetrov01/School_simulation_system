package ccpetrov01.studentApp.services.studentProfile;

import ccpetrov01.studentApp.Dtos.StudentProfileDtoView;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.models.StudentProfile;
import ccpetrov01.studentApp.repository.StudentProfileRepository;
import ccpetrov01.studentApp.requests.UpdateStudentProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentProfileService implements IStudentProfileService{
    private final StudentProfileRepository studentProfileRepository;
    @Override
    public StudentProfile addStudentProfile(StudentProfile studentProfile) {
        try {
            return studentProfileRepository.save(studentProfile);
        }catch(GlobalException e){
            throw new GlobalException("Something went wrong with adding new studentProfile!" , e);
        }
    }

    @Override
    public StudentProfile getStudentProfileById(Integer student_id) {
        return studentProfileRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with this id" + student_id + " doesn't exists"));
    }

    @Override
    public void deleteStudentProfileById(Integer student_id) {
            studentProfileRepository.findById(student_id).ifPresentOrElse(
            studentProfileRepository::delete
            , ()-> {
                       throw new ResourceNotFoundException("Student with this id " + student_id + "is not found and cannot be deleted!");
                    });
    }

    @Override
    public StudentProfile updateStudentProfileById(UpdateStudentProfileRequest profileRequest, Integer student_id) {
        StudentProfile studentProfile =  studentProfileRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id you entered" + student_id + " is not found!"));

        studentProfile.setSubjects(profileRequest.getSubjects());
        studentProfile.setGrade(profileRequest.getGrade());
        studentProfile.setClasses_participated(profileRequest.getClasses_participated());
        studentProfile.setClasses_missed(profileRequest.getClasses_missed());

        return studentProfileRepository.save(studentProfile);
    }

    @Override
    public Double findAverageGradesByStudent(Integer student_id , String subject) {
        try {
            return studentProfileRepository.findAverageGradeForStudentAndSubject(student_id, subject);
        }catch(GlobalException e){
            throw new GlobalException("Couldn't return student average grade! something went wrong",e);
        }
    }
    @Override
    public List<StudentProfile> findGradesBySubject(String subject , Integer student_id) {
        List<StudentProfile> profileList = studentProfileRepository.findByStudent_IdAndSubjects(student_id, subject);
        if(profileList.isEmpty()){
            throw new ResourceNotFoundException("Student doesn't have any grades yet for subject you search! " + subject);
        }
        return profileList;
    }

    @Override
    public StudentProfileDtoView toStudentProfileDto(StudentProfile studentProfile){
        StudentProfileDtoView studentProfileDtoView = new StudentProfileDtoView();
        studentProfileDtoView.setSubjects(studentProfile.getSubjects());
        studentProfileDtoView.setGrade(studentProfile.getGrade());
        studentProfileDtoView.setClasses_participated(studentProfile.getClasses_participated());
        studentProfileDtoView.setClasses_missed(studentProfile.getClasses_missed());

        return studentProfileDtoView;
    }
    @Override
    public List<StudentProfileDtoView> toStudentProfileDtoList(List<StudentProfile> studentProfileList){
        return studentProfileList
                .stream()
                .map(this::toStudentProfileDto)
                .toList();
    }
}
