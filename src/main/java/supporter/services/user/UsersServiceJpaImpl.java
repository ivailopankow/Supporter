package supporter.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import supporter.models.User;
import supporter.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Service
@Primary
public class UsersServiceJpaImpl {

    private final UserRepository userRepository;

    @Autowired
    public UsersServiceJpaImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return this.userRepository.findOne(id);
    }

    public User create(User user) {

        return this.userRepository.save(user);
    }

    public User edit(User user) {
        return this.userRepository.save(user);
    }

    public void deleteById(Long id) {
        this.userRepository.delete(id);
    }

    public boolean authenticate(String username, String password) {
        return Objects.equals(username, password);
    }

    public User login(String username, String password) {
        return null;
    }

    public User register(String username, String password, String fullName) {
        return null;
    }

    public void setPassword(String username, String newPassword) {

    }

}
