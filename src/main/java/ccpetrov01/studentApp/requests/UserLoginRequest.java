package ccpetrov01.studentApp.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
