package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.TeacherDtoView;
import ccpetrov01.studentApp.exceptions.AlreadyExistsException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.Teacher;
import ccpetrov01.studentApp.requests.UpdateTeacherRequest;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.teacher.ITeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor

public class TeacherController {
    private final ITeacherService teacherService;
    @PostMapping("/api/v1/teacher/teacher/add/teacher")
    public ResponseEntity<ApiResponse> addTeacher(@Valid @RequestBody Teacher teacher){
        try{
            Teacher teacherAdd = teacherService.addTeacher(teacher);
            TeacherDtoView teacherDtoView = teacherService.teacherDtoView(teacherAdd);
            return ResponseEntity.ok(ApiResponse.success("Successfully adding new Teacher!", teacherDtoView));
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.failure("There is some data you entered that already existed for other teacher!",
                            List.of("ALREADY_EXISTS_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/get/teacher/{teacherId}")
    public ResponseEntity<ApiResponse> getTeacherById(@PathVariable Integer teacherId){
        try{
            Teacher teacher = teacherService.getTeacherById(teacherId);
            TeacherDtoView teacherDtoView = teacherService.teacherDtoView(teacher);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning teacher with give id!", teacherDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Teacher with given id didn't exists! ",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @PutMapping("/api/v1/teacher/update/teacher/{teacherId}")
    public ResponseEntity<ApiResponse> updateTeacher(@RequestBody UpdateTeacherRequest request,
                                                     @PathVariable Integer teacherId){
        try{
            Teacher teacher = teacherService.updateTeacher(request, teacherId);
            TeacherDtoView dtoView = teacherService.teacherDtoView(teacher);
            return ResponseEntity.ok(ApiResponse.success("Successfully updatig teachers information with id:" + teacher, dtoView));
        }catch(ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Teacher with entered id:" + teacherId + " doesn't exists!" ,
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/get/teacher/all")
    public ResponseEntity<ApiResponse> getAllTeachers(){
        try{
            List<Teacher> teachers = teacherService.getAllTeachers();
            List<TeacherDtoView> dtoViews = teacherService.teacherDtoViewToList(teachers);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning all teachers!", dtoViews));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("There are not teachers yet, try adding one!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));

        }
    }
    @GetMapping("/api/v1/admin/get/teacher/by/{firstname}/{lastname}")
    public ResponseEntity<ApiResponse> getTeacherByFirstnameAndLastname(@Valid @PathVariable String firstname,
                                                                        @Valid @PathVariable String lastname){
        try{
            List<Teacher> teachers = teacherService.getTeacherByFirstnameAndLastname(firstname, lastname);
            List<TeacherDtoView> dtoViews = teacherService.teacherDtoViewToList(teachers);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning all teacher with firstname:" + firstname
            + " and lastname:" + lastname , dtoViews));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Teachers/Teacher with given firstname and lastname doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/get/teacher/by/{firstname}")
    public ResponseEntity<ApiResponse> getTeacherByFirstname(@Valid @PathVariable String firstname){
        try{
            List<Teacher> teachers = teacherService.getTeacherByFirstname(firstname);
            List<TeacherDtoView> dtoViews = teacherService.teacherDtoViewToList(teachers);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning all teachers with firstname:" + firstname,
                    dtoViews));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("Teachers/Teacher with given firstname is not found!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
}

