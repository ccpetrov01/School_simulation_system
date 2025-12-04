package ccpetrov01.studentApp.controllers;

import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.jwt.JWTUtils;
import ccpetrov01.studentApp.requests.UserLoginRequest;
import ccpetrov01.studentApp.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    @PostMapping("api/v1/auth/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLoginRequest loginRequest){
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                            loginRequest.getPassword()));
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(ApiResponse.success("Successfully logging in!", token));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(ApiResponse.failure("User with this username and password is not found!",
                            List.of("NOT_FOUND_EXCEPTION" , "If you don't have account register first!")));
        }
    }


}
