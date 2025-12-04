package ccpetrov01.studentApp.Dtos;

import ccpetrov01.studentApp.enums.Gender;
import lombok.Data;

@Data
public class StudentDtoView {
        private String firstname;
        private String lastname;
        private Gender gender;
        private String classes;
        private StudentProfileDtoView studentProfile;
}
