package ccpetrov01.studentApp.services.student;

import ccpetrov01.studentApp.Dtos.StudentDtoView;
import ccpetrov01.studentApp.Dtos.StudentProfileDtoView;
import ccpetrov01.studentApp.exceptions.AlreadyExistsException;
import ccpetrov01.studentApp.exceptions.DuplicateResourceException;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.repository.StudentRepository;
import ccpetrov01.studentApp.requests.UpdateStudentRequests;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;


    @Override
    public Student addStudent(Student student) {
        try {
            return studentRepository.save(student);
        }catch(GlobalException e){
            throw new GlobalException("Something went wrong with adding new student!" , e);
        }
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User with this id:" + id + " is not found"));
    }

    @Override
    public List<Student> getStudentsByFirstNameAndLastName(String firstname, String lastname) {
        List<Student> students = studentRepository.findByFirstnameAndLastname(firstname, lastname);

        if (students.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Users with firstname:" + firstname + " and lastname:" + lastname + " are not found!"
            );
        }

        return students;
    }

    @Override
    public List<Student> getStudentsByFirstName(String firstname) {
        List<Student> students = studentRepository.findByFirstname(firstname);

        if(students.isEmpty())
            throw new ResourceNotFoundException(
                "Users with firstname:" + firstname + " are not found!"
        );

        return students;
    }

    @Override
    public void deleteStudentById(Integer id) {
         studentRepository.findById(id).ifPresentOrElse(
                 studentRepository :: delete ,
        () ->{
            throw new ResourceNotFoundException("User with this id:" + id + " is not found");
         });
    }

    @Override
    public Student updateStudent(UpdateStudentRequests request, Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id:" + studentId + " doesn't exists!"));


        if(request.getPhoneNumber() != null && !student.getPhoneNumber().equals(request.getPhoneNumber())){
            if(studentRepository.existsByPhoneNumber(request.getPhoneNumber())){
                throw new DuplicateResourceException("phoneNumber already in use:" + request.getPhoneNumber() + "|");
            }
            student.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getFirstname() != null && !request.getFirstname().isBlank()) {
            student.setFirstname(request.getFirstname());
        }


        if (request.getLastname() != null && !request.getLastname().isBlank()) {
            student.setLastname(request.getLastname());
        }

        return studentRepository.save(student);

    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        if(students.isEmpty()){
            throw new ResourceNotFoundException("No students exists yet , try add students first!");
        }

        return students;
    }


    @Override
    public StudentDtoView studentDtoView(Student student){
        StudentDtoView dtoView = new StudentDtoView();
        dtoView.setFirstname(student.getFirstname());
        dtoView.setLastname(student.getLastname());
        dtoView.setGender(student.getGender());
        dtoView.setClasses(student.getClasses());

        StudentProfileDtoView studentProfileDtoView = new StudentProfileDtoView();
        studentProfileDtoView.setSubjects(dtoView.getStudentProfile().getSubjects());
        studentProfileDtoView.setGrade(dtoView.getStudentProfile().getGrade());
        studentProfileDtoView.setClasses_participated(dtoView.getStudentProfile().getClasses_participated());
        studentProfileDtoView.setClasses_missed(dtoView.getStudentProfile().getClasses_missed());

        dtoView.setStudentProfile(studentProfileDtoView);
        return dtoView;
    }
    @Override
    public List<StudentDtoView> studentDtoViewList(List<Student> studentList){
        return studentList
                .stream()
                .map(this::studentDtoView)
                .toList();
    }

}


