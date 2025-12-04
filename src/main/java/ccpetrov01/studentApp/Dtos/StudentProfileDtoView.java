package ccpetrov01.studentApp.Dtos;

import ccpetrov01.studentApp.enums.Subjects;
import ccpetrov01.studentApp.models.Student;
import lombok.Data;

@Data
public class StudentProfileDtoView {
    private Subjects subjects;
    private Long grade;
    private Long classes_participated;
    private Long classes_missed;
    private StudentDtoView student;
}
