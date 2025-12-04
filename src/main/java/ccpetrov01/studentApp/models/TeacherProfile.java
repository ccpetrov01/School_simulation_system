package ccpetrov01.studentApp.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeacherProfile {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_generator"
    )
    @SequenceGenerator(
            name="product_generator",
            sequenceName = "product_generator",
            allocationSize = 1
    )
    private Integer id;
    private Double experience;
    private Long classes_participated;
    private Long paid_vacation_days;

    @OneToOne(mappedBy = "teacherProfile")
    private Teacher teacher;
}
