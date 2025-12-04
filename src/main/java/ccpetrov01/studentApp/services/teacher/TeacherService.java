package ccpetrov01.studentApp.services.teacher;

import ccpetrov01.studentApp.Dtos.TeacherDtoView;
import ccpetrov01.studentApp.Dtos.TeacherProfileDtoView;
import ccpetrov01.studentApp.exceptions.AlreadyExistsException;
import ccpetrov01.studentApp.exceptions.DuplicateResourceException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.Teacher;
import ccpetrov01.studentApp.repository.TeacherRepository;
import ccpetrov01.studentApp.requests.UpdateTeacherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public Teacher addTeacher(Teacher teacher) {
        if (teacherRepository.existsByPhoneNumber(teacher.getPhoneNumber())) {
            throw new AlreadyExistsException("Teacher already exists with this phoneNumber:" + teacher.getPhoneNumber() + " you cant use other teacher data!");
        }
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Integer teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with give id:" + teacherId + " doesn't exists!"));
    }

    @Override
    public void deleteTeacherById(Integer teacherId) {
        teacherRepository.findById(teacherId).ifPresentOrElse(
                teacherRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Teacher with give id:" + teacherId + " doesn't exists and cannot be deleted!");
                }
        );
    }

    @Override
    public Teacher updateTeacher(UpdateTeacherRequest request, Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with this id:" + teacherId + " doesn't exists!"));


        if (request.getPhoneNumber() != null && !teacher.getPhoneNumber().equals(request.getPhoneNumber())) {
            if (teacherRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                throw new DuplicateResourceException("phoneNumber already in use:" + request.getPhoneNumber());
            }
            teacher.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getFirstname() != null && !request.getFirstname().isBlank()) {
            teacher.setFirstname(request.getFirstname());
        }


        if (request.getLastname() != null && !request.getLastname().isBlank()) {
            teacher.setLastname(request.getLastname());
        }

        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();

        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("No teachers exists yet , try add students first!");
        }

        return teachers;
    }

    @Override
    public List<Teacher> getTeacherByFirstnameAndLastname(String firstname, String lastname) {
        List<Teacher> teachers = teacherRepository.findByFirstnameAndLastname(firstname, lastname);

        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("No teachers exists yet , try add students first!");
        }

        return teachers;
    }

    @Override
    public List<Teacher> getTeacherByFirstname(String firstname) {
        List<Teacher> teachers = teacherRepository.findByFirstname(firstname);

        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("No teachers exists yet , try add students first!");
        }

        return teachers;
    }

    @Override
    public TeacherDtoView teacherDtoView(Teacher teacher) {
        TeacherDtoView teacherDtoView = new TeacherDtoView();
        teacherDtoView.setFirstname(teacher.getFirstname());
        teacherDtoView.setLastname(teacher.getLastname());
        teacherDtoView.setGender(teacher.getGender());

        TeacherProfileDtoView teacherProfileDtoView = new TeacherProfileDtoView();
        teacherProfileDtoView.setExperience(teacher.getTeacherProfile().getExperience());
        teacherProfileDtoView.setClasses_participated(teacher.getTeacherProfile().getClasses_participated());
        teacherProfileDtoView.setPaid_vacation_days(teacher.getTeacherProfile().getPaid_vacation_days());

        teacherDtoView.setTeacherProfileDtoView(teacherProfileDtoView);
        return teacherDtoView;
    }

    @Override
    public List<TeacherDtoView> teacherDtoViewToList(List<Teacher> teacherList) {
        return teacherList
                .stream()
                .map(this::teacherDtoView)
                .toList();
    }

}
