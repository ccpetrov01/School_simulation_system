package ccpetrov01.studentApp.requests;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateStudentRequests {
    String firstname;
    String lastname;
    @Column(nullable = false , unique = true)
    String phoneNumber;
}
