package ccpetrov01.studentApp.Dtos;


import lombok.Data;

import java.util.List;

@Data
public class SchoolDtoView {
    private String name;
    private Double rating;
    private String description;
    private String address;
    private List<StudentDtoView> students;
    private List<TeacherDtoView> teachers;
}
