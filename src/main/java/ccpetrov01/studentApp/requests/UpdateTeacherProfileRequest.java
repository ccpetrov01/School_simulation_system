package ccpetrov01.studentApp.requests;

import lombok.Data;

@Data
public class UpdateTeacherProfileRequest {
    private Double experience;
    private Long classes_participated;
    private Long paid_vacation_days;
}
