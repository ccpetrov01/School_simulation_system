package ccpetrov01.studentApp.models;

import ccpetrov01.studentApp.enums.Subjects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentProfile {

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
    @Enumerated(EnumType.STRING)
    @NotNull
    private Subjects subjects;
    @NotNull
    private Long grade;

    private Long classes_participated;

    private Long classes_missed;

    @OneToOne(mappedBy = "studentProfile")
    private Student student;

}
