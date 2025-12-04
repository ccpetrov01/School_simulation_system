package ccpetrov01.studentApp.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSchoolRequest {
    private String name;
    @NotNull
    private Double rating;
    private String description;
    @NotBlank
    private String address;
}
