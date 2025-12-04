package ccpetrov01.studentApp.services.teacher;

import ccpetrov01.studentApp.Dtos.TeacherDtoView;
import ccpetrov01.studentApp.models.Teacher;
import ccpetrov01.studentApp.requests.UpdateTeacherRequest;

import java.util.List;

public interface ITeacherService {
    Teacher addTeacher(Teacher teacher);
    Teacher getTeacherById(Integer id);
    void deleteTeacherById(Integer id);
    Teacher updateTeacher(UpdateTeacherRequest request, Integer teacherId);

    List<Teacher> getAllTeachers();

    List<Teacher> getTeacherByFirstnameAndLastname(String firstname, String lastname);

    List<Teacher> getTeacherByFirstname(String firstname);

    TeacherDtoView teacherDtoView(Teacher teacher);
    List<TeacherDtoView> teacherDtoViewToList(List<Teacher> teacherList);
}
