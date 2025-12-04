package ccpetrov01.studentApp.services.user;

import ccpetrov01.studentApp.Dtos.UserDtoView;
import ccpetrov01.studentApp.models.User;
import ccpetrov01.studentApp.requests.UserUpdateRequest;

public interface IUserService {
    User register(User request);
    void deleteUserById(Integer userId);
    User updateUser(UserUpdateRequest request, Integer userId);
    User getUserById(Integer userId);
    UserDtoView ConvertUserToDto(User user);
}
