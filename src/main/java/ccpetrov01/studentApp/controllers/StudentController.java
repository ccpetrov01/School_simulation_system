package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.StudentDtoView;
import ccpetrov01.studentApp.exceptions.AlreadyExistsException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.Student;
import ccpetrov01.studentApp.requests.UpdateStudentRequests;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.student.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class StudentController {

   private final IStudentService studentService;
    @PostMapping("/api/v1/student/student/add")
    public ResponseEntity<ApiResponse> addStudent(@Valid @RequestBody Student student){
        try{
                studentService.addStudent(student);
                StudentDtoView dtoView = studentService.studentDtoView(student);
                return ResponseEntity.ok(ApiResponse.success("Successfully adding new Student!", dtoView));
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.failure(
                            "Conflict occurred while saving student",
                            List.of("ALREADY_EXISTS_ERROR", e.getMessage())
                    ));
        }
    }
    @GetMapping("/api/v1/admin/student/search/{id}/search")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Integer id){
        try{
                Student student = studentService.getStudentById(id);
                StudentDtoView dtoView = studentService.studentDtoView(student);
                return ResponseEntity.ok(ApiResponse.success("Successfully finding the student!", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Searched student is not found!",
                            List.of("NOT_EXISTS_ERROR" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/student/search/{firstname}/{lastname}/search")
    public ResponseEntity<ApiResponse> getStudentsByFirstNameAndLastName(@Valid @PathVariable String firstname,
                                                                        @Valid @PathVariable String lastname){
        try{
            List<Student> student = studentService.getStudentsByFirstNameAndLastName(firstname , lastname);
            List<StudentDtoView> viewList = studentService.studentDtoViewList(student);
            return  ResponseEntity.ok(ApiResponse.success("Successfully finding students!", viewList));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Student with" + firstname + " and this " + lastname + " doesn't exists!",
                            List.of("NOT_EXISTS_ERROR", e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/student/search/{firstname}/search")
    public ResponseEntity<ApiResponse> getStudentsByFirstName(@Valid @PathVariable String firstname){
        try{
            List<Student> studentList = studentService.getStudentsByFirstName(firstname);
            List<StudentDtoView> dtoViews = studentService.studentDtoViewList(studentList);
            return ResponseEntity.ok(ApiResponse.success("Successfully finding students!", dtoViews));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Student with" + firstname + "doesn't exists!",
                            List.of("NOT_EXISTS_ERROR" , e.getMessage())));
        }
    }
    @DeleteMapping("/api/v1/admin/student/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStudentById(@PathVariable Integer id){
        try{
            studentService.deleteStudentById(id);
            return ResponseEntity.ok(ApiResponse.success("Successfully deleting student with id" + id , null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Student with this id doesn't exists!",
                            List.of("NOT_EXISTS_ERROR" , e.getMessage())));
        }
    }

    @PutMapping("/api/v1/student/student/update/{studentId}")
    public ResponseEntity<ApiResponse> updateStudent(@RequestBody UpdateStudentRequests request,
                                                     @PathVariable Integer studentId){
        try{
            Student student = studentService.updateStudent(request , studentId);
            StudentDtoView studentDtoView = studentService.studentDtoView(student);
            return ResponseEntity.ok(ApiResponse.success("Successfully updated student with id" + studentId , studentDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Student with the id you entered doesn't exists!",
                            List.of("NOT_EXISTS_ERROR" , e.getMessage())));
        }
    }

    @GetMapping("/api/v1/admin/student/search/all")
    public ResponseEntity<ApiResponse> getAllStudents(){
        try{
            List<Student> students = studentService.getAllStudents();
            List<StudentDtoView> dtoStudents = studentService.studentDtoViewList(students);
            return ResponseEntity.ok(ApiResponse.success("Successfully getting all the students!", dtoStudents));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("No Students Found!",
                            List.of("NOT_EXISTS_ERROR" , e.getMessage())));
        }

    }

}
