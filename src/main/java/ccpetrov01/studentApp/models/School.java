package ccpetrov01.studentApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class School {
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
    @Column(nullable = false , unique = true)
    private String name;
    @NotNull
    private Double rating;
    private String description;
    @NotBlank
    private String address;

    @OneToMany(mappedBy = "school")
    private List<Student> students;

    @OneToMany(mappedBy = "school")
    private List<Teacher> teachers;

}
