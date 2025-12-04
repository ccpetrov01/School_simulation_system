package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.StudentProfileDtoView;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.StudentProfile;
import ccpetrov01.studentApp.requests.UpdateStudentProfileRequest;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.studentProfile.IStudentProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class StudentProfileController {
    private final IStudentProfileService iStudentProfileService;
    @PostMapping("/api/v1/student/studentProfile/add")
    public ResponseEntity<ApiResponse> addStudentProfile(@Valid @RequestBody StudentProfile studentProfile){
        try{
            iStudentProfileService.addStudentProfile(studentProfile);
            StudentProfileDtoView studentProfileDtoView = iStudentProfileService.toStudentProfileDto(studentProfile);
            return ResponseEntity.ok(ApiResponse.success("Successfully adding studentProfile to student", studentProfileDtoView));
        }catch(GlobalException e){
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.failure("Conflict occurred while saving studentProfile",
                    List.of("GLOBAL_EXCEPTION_ERROR" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/studentProfile/get/student/{studentId}")
    public ResponseEntity<ApiResponse> getStudentProfileById(@PathVariable Integer studentId){
        try{
            StudentProfile studentProfile = iStudentProfileService.getStudentProfileById(studentId);
            StudentProfileDtoView studentProfileDtoView = iStudentProfileService.toStudentProfileDto(studentProfile);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning studentProfile with id: " + studentId , studentProfileDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("studentProfile with id" + studentId + " you entered doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @DeleteMapping("/api/v1/admin/studentProfile/delete/student/{studentId}")
    public ResponseEntity<ApiResponse> deleteStudentProfileById(@PathVariable Integer studentId){
        try{
            iStudentProfileService.deleteStudentProfileById(studentId);
            return ResponseEntity.ok(ApiResponse.success("Successfully deleting studentProfile with id: " + studentId , null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("studentProfile with given id: " + studentId + " doesn't exist and cannot be deleted",
                            List.of("NOT_FOUND_ERRORS" , e.getMessage())));
        }
    }

    @PutMapping("/api/v1/student/studentProfile/update/student/{studentId}")
    public ResponseEntity<ApiResponse> updateStudentProfileById(@Valid @RequestBody UpdateStudentProfileRequest update,
                                                                @PathVariable Integer studentId)
    {
        try{
            StudentProfile studentProfile = iStudentProfileService.updateStudentProfileById(update, studentId);
            StudentProfileDtoView profileDtoView = iStudentProfileService.toStudentProfileDto(studentProfile);
            return ResponseEntity.ok(ApiResponse.success("Successfully updating studentProfile with id:" + studentId , profileDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("studentProfile with given id:" + studentId +" doesn't exists",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/studentProfile/get/student/grades/average/{studentId}")
    public ResponseEntity<ApiResponse> findAverageGradesByStudent(@PathVariable Integer studentId ,
                                                                  @RequestParam String subject){
        try{
            Double averageGrade = iStudentProfileService.findAverageGradesByStudent(studentId, subject);
            return ResponseEntity.ok(ApiResponse.success
                    ("Successfully returning average grades for studentProfile with id:" + studentId, averageGrade));
        }catch(GlobalException e){
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.failure("Something went wrong when fetching data",
                            List.of("GLOBAL_EXCEPTION_ERROR" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/studentProfile/get/student/grades/all/{studentId}")
    public ResponseEntity<ApiResponse> findGradesBySubject(@RequestParam String subject ,
                                                           @PathVariable Integer studentId){
        try{
            List<StudentProfile> studentProfileList = iStudentProfileService.findGradesBySubject(subject, studentId);
            List<StudentProfileDtoView> dtoViewList = iStudentProfileService.toStudentProfileDtoList(studentProfileList);
            return ResponseEntity.ok(ApiResponse
                    .success("Successfully returning all grades of student with studentProfile id:" + studentId
                    , dtoViewList));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("studentProfile with given id is not found:" + studentId,
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }

}
