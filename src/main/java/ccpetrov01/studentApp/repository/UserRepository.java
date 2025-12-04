package ccpetrov01.studentApp.repository;

import ccpetrov01.studentApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPassword(String password);

    Optional<User> findByUsername(String username);
}
