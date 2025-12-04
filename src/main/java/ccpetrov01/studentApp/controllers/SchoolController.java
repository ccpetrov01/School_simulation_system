package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.SchoolDtoView;
import ccpetrov01.studentApp.exceptions.GlobalException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.School;
import ccpetrov01.studentApp.requests.UpdateSchoolRequest;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.school.ISchoolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class SchoolController {
    private final ISchoolService schoolService;
    @PostMapping("/api/v1/admin/school/add/school")
    public ResponseEntity<ApiResponse> addSchool(@Valid @RequestBody  School school){
        try{
            schoolService.addSchool(school);
            SchoolDtoView schoolDtoView = schoolService.schoolDtoView(school);
            return ResponseEntity.ok(ApiResponse.success("Successfully adding new school!", schoolDtoView));
        }catch(GlobalException e){
            return ResponseEntity.status(CONFLICT)
                    .body(ApiResponse.failure("School was not added successfully"
                            , List.of("GLOBAL_EXCEPTION_ERROR" , e.getMessage())));
        }
    }

    @GetMapping("/api/v1/admin/school/get/{schoolId}")
    public ResponseEntity<ApiResponse> getSchoolById(@PathVariable Integer schoolId){
        try{
            School school = schoolService.getSchoolById(schoolId);
            SchoolDtoView schoolDtoView = schoolService.schoolDtoView(school);
            return ResponseEntity.ok(ApiResponse.success("Successfully finding school with id:" + schoolId, schoolDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("School with given id" + schoolId + " doesn't exists",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @DeleteMapping("/api/v1/admin/school/{schoolId}")
    public ResponseEntity<ApiResponse> deleteSchoolById(@PathVariable Integer schoolId){
        try{
            schoolService.deleteSchoolById(schoolId);
            return ResponseEntity.ok(ApiResponse.success("Successfully deleting school with id:" + schoolId, null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("School with give id:" + schoolId + " doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @PutMapping("/api/v1/admin/school/update/{schoolId}")
    public ResponseEntity<ApiResponse> updateSchoolInformation(@RequestBody UpdateSchoolRequest request,
                                                               @PathVariable Integer schoolId){
        try{
                School school = schoolService.updateSchoolInformation(request,schoolId);
                SchoolDtoView dtoView = schoolService.schoolDtoView(school);
                return ResponseEntity.ok(ApiResponse.success("Successfully updating school information", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("School with give id:" + schoolId + " doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }

    @GetMapping("/api/v1/admin/school/get/all")
    public ResponseEntity<ApiResponse> getAllSchools(){
        try{
            List<School> schools = schoolService.getAllSchools();
            List<SchoolDtoView> dtoView = schoolService.schoolDtoViewList(schools);
            return ResponseEntity.ok(ApiResponse.success("Successfully getting all school information", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("There are no schools added yet , try adding one!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/school/find/ratings")
    public ResponseEntity<ApiResponse> findAllRatingsOrderedAsc(){
        try{
            List<Double> schools = schoolService.findAllRatingsOrderedAsc();
            return ResponseEntity.ok(ApiResponse.success("Successfully getting all schools rating ASC order!", schools));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("There are no schools added yet , try adding one!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/school/find/by/{school_name}")
    public ResponseEntity<ApiResponse> findByName(@PathVariable String school_name){
        try{
            School school = schoolService.findByName(school_name);
            SchoolDtoView dtoView = schoolService.schoolDtoView(school);
            return ResponseEntity.ok(ApiResponse.success("Successfully getting all schools rating ASC order!", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("There are no schools added yet , try adding one!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }

}
