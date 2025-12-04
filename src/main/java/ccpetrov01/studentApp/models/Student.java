package ccpetrov01.studentApp.models;

import ccpetrov01.studentApp.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
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
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @Column(nullable = false , unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotBlank
    private String classes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private StudentProfile studentProfile;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
