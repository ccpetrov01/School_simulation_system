package ccpetrov01.studentApp.services.student;

import ccpetrov01.studentApp.Dtos.StudentDtoView;
import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.requests.UpdateStudentRequests;

import java.util.List;

public interface IStudentService {

    Student addStudent(Student student);
    Student getStudentById(Integer id);
    void deleteStudentById(Integer id);
    Student updateStudent(UpdateStudentRequests request, Integer studentId);

    List<Student> getAllStudents();

    List<Student> getStudentsByFirstNameAndLastName(String firstname, String lastname);

    List<Student> getStudentsByFirstName(String firstname);

    StudentDtoView studentDtoView(Student student);
    List<StudentDtoView> studentDtoViewList(List<Student> studentList);

}
