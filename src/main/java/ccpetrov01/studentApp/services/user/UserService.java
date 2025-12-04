package ccpetrov01.studentApp.services.user;

import ccpetrov01.studentApp.Dtos.UserDtoView;
import ccpetrov01.studentApp.enums.Roles;
import ccpetrov01.studentApp.exceptions.AlreadyExistsException;
import ccpetrov01.studentApp.exceptions.NullException;
import ccpetrov01.studentApp.exceptions.ResourceNotFoundException;
import ccpetrov01.studentApp.models.User;
import ccpetrov01.studentApp.repository.UserRepository;
import ccpetrov01.studentApp.requests.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User register(User request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AlreadyExistsException("Username already exists! Try a different one.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("Email is already used! Try a different one.");
        }
        if(request.getPassword() == null )
        {
            throw new NullException("Password can't be blank try adding password!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            user.setRoles(request.getRoles());
        } else {
            throw new NullException("you need to select if you are student or teacher , cannot leave it blank!");
        }

        return userRepository.save(user);
    }


    @Override
    public void deleteUserById(Integer userId) {
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete,
                ()-> {
                    throw new ResourceNotFoundException("There is no user with that userId and cannot be deleted!");
        });
    }

    @Override
    public User updateUser(UserUpdateRequest request, Integer userId) {
        User user = userRepository.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User with this userId doesn't exists !"));

        if(request.getUsername() != null && !request.getUsername().equals(user.getUsername())){
            if(userRepository.existsByUsername(user.getUsername())){
                throw new AlreadyExistsException("Username you entered already exists and cannot be created as new account , try different username!");
            }
            user.setUsername(request.getUsername());
        }
        if(request.getPassword() !=null && !request.getPassword().equals(user.getPassword())){
            if(userRepository.existsByPassword(user.getPassword())){
                throw new AlreadyExistsException("You can't use this password, try different!");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with this userId doesn't exists !"));
    }

    @Override
    public UserDtoView ConvertUserToDto(User user) {
        UserDtoView userDtoView = new UserDtoView();
        userDtoView.setEmail(user.getEmail());
        return userDtoView;
    }
}
