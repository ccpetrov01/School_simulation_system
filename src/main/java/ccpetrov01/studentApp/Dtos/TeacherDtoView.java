package ccpetrov01.studentApp.Dtos;

import ccpetrov01.studentApp.enums.Gender;
import lombok.Data;

@Data
public class TeacherDtoView {
    private String firstname;
    private String lastname;
    private Gender gender;
    private TeacherProfileDtoView teacherProfileDtoView;
}
