package ccpetrov01.studentApp.requests;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Column(nullable = false , unique = true)
    String username;
    @Column(nullable = false)
    String password;
}
