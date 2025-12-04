package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.Dtos.UserDtoView;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.User;
import ccpetrov01.studentApp.responses.ApiResponse;
import ccpetrov01.studentApp.services.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class RegisterController {
        private final IUserService userService;
    @PostMapping("api/v1/auth/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User request){
        try{
            userService.register(request);
            UserDtoView userDtoView = userService.ConvertUserToDto(request);
            return ResponseEntity.ok(ApiResponse.success("You register successfully!", userDtoView));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("User with this username and password is not found!",
                            List.of("NOT_FOUND_EXCEPTION" , e.getMessage())));
        }
    }
}
