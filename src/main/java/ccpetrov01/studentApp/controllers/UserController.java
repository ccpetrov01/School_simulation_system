package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.UserDtoView;
import ccpetrov01.studentApp.exceptions.AlreadyExistsException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.User;
import ccpetrov01.studentApp.requests.UserUpdateRequest;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @DeleteMapping("/api/v1/admin/user/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Integer userId){
        try{
            userService.deleteUserById(userId);
            return ResponseEntity.ok(ApiResponse.success("Successfully deleting user with given id!", null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("User with given id doesn't exists!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @PutMapping("/api/v1/student/user/update/{userId}")
    public ResponseEntity<ApiResponse> updateStudent(@Valid @RequestBody UserUpdateRequest request,
                                                  @PathVariable Integer userId){
        try{
            User user = userService.updateUser(request, userId);
            UserDtoView dtoView = userService.ConvertUserToDto(user);
            return ResponseEntity.ok(ApiResponse.success("Successfully updating user with given id!", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("User with given id doesn't exists and cannot be updated!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }

    @PutMapping("/api/v1/teacher/user/update/{userId}")
    public ResponseEntity<ApiResponse> updateTeacher(@Valid @RequestBody UserUpdateRequest request,
                                                  @PathVariable Integer userId){
        try{
            User user = userService.updateUser(request, userId);
            UserDtoView dtoView = userService.ConvertUserToDto(user);
            return ResponseEntity.ok(ApiResponse.success("Successfully updating user with given id!", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("User with given id doesn't exists and cannot be updated!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
    @GetMapping("/api/v1/admin/user/get/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Integer userId){
        try{
            User user = userService.getUserById(userId);
            UserDtoView dtoView = userService.ConvertUserToDto(user);
            return ResponseEntity.ok(ApiResponse.success("Successfully returning studen with given id!", dtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("User with give id doesn't exists and cannot be found!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
}
