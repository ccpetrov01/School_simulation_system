package ccpetrov01.studentApp.Dtos;

import lombok.Getter;

@Getter
public class LoginDtoView {

    private String token;
    public LoginDtoView(String token) {
        this.token = token;
    }


}
