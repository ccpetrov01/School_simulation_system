package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.TeacherDtoView;
import ccpetrov01.studentApp.Dtos.TeacherProfileDtoView;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.Teacher;
import ccpetrov01.studentApp.models.TeacherProfile;
import ccpetrov01.studentApp.projection.TeacherParticipation;
import ccpetrov01.studentApp.requests.UpdateTeacherProfileRequest;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.teacherProfile.ITeacherProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class TeacherProfileController {
    private final ITeacherProfileService teacherProfileService;
    @PostMapping("/api/v1/teacher/teacherProfile/add/teacherProfile")
    public ResponseEntity<ApiResponse> addTeacherProfile(@RequestBody TeacherProfile teacherProfile){
        try{
            TeacherProfile teacher = teacherProfileService.addTeacherProfile(teacherProfile);
            TeacherProfileDtoView profileDtoView = teacherProfileService.toTeacherProfileDto(teacher);
            return ResponseEntity.ok(ApiResponse.success("Successfully adding new teacherProfile!" , profileDtoView));
        }catch(GlobalException e){
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.failure("Conflict occurred while saving teacherProfile",
                            List.of("GLOBAL_EXCEPTION_ERROR", e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/teacherProfile/get/byId/{teacherId}")
    public ResponseEntity<ApiResponse> getTeacherById(@PathVariable Integer teacherId){
        try{
            TeacherProfile teacherProfile = teacherProfileService.getTeacherById(teacherId);
            TeacherProfileDtoView dtoView = teacherProfileService.toTeacherProfileDto(teacherProfile);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning teacher with given id:" , dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("teacherProfile with given id:" + teacherId + " doesn't exists!" ,
                            List.of("NOT_FOUND_EXCEPTION" ,e.getMessage())));
        }
    }

    @DeleteMapping("/api/v1/admin/teacherProfile/delete/byId/{teacherId}")
    public ResponseEntity<ApiResponse> deleteTeacherById(@PathVariable Integer teacherId){
        try{
            teacherProfileService.deleteTeacherById(teacherId);
            return ResponseEntity.ok(ApiResponse.success("Successfully deleting teacherProfile with id:" + teacherId
            , null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.success("teacherProfile with give id:" + teacherId + " doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }

    }
    @PutMapping("/api/v1/teacher/teacherProfile/update/{teacherId}")
    public ResponseEntity<ApiResponse> updateTeacherProfile(@RequestBody UpdateTeacherProfileRequest request,
                                                            @PathVariable Integer teacherId){
        try{
            TeacherProfile teacherProfile = teacherProfileService.updateTeacherProfile(request, teacherId);
            TeacherProfileDtoView dtoView = teacherProfileService.toTeacherProfileDto(teacherProfile);
            return ResponseEntity.ok(ApiResponse.success("Successfully updating teacherProfile with id:" + teacherId
                    ,dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("teacherProfile with given id:" + teacherId + " doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/teacherProfile/get/teachers/by/participation")
    public ResponseEntity<ApiResponse> findClassParticipationByTeacherASC(){
        try {
            List<TeacherParticipation> teachers = teacherProfileService.findClassParticipationByTeacherASC();
            return ResponseEntity.ok(ApiResponse.success("Successfully returning average participation by teacher!", teachers));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("There are no teachers yet added or something went wrong!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/teacherProfile/get/teachers/by/experience")
    public ResponseEntity<ApiResponse> findAvgExperienceBy(){
        try{
            Double teachersExp = teacherProfileService.findAvgExperienceBy();
            return ResponseEntity.ok(ApiResponse.success("Successfully returning average teachers exceprience!", teachersExp));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("There are not teacher yet and cannot be returned average experience!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }


}
