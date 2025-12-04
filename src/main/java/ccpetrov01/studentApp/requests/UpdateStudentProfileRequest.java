package ccpetrov01.studentApp.requests;

import ccpetrov01.studentApp.enums.Subjects;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateStudentProfileRequest {

    @Enumerated(EnumType.STRING)
    @NotBlank
    private Subjects subjects;
    @NotBlank
    private Long grade;

    private Long classes_participated;

    private Long classes_missed;
}
