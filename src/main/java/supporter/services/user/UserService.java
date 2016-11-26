package supporter.services.user;

import org.springframework.security.core.userdetails.UserDetails;
import supporter.models.User;

import java.util.List;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public interface UserService {

    List<User> findAll();
    User findById(Integer id);
    User findByEmail(String username);
    User create(User user);
    User edit(User user);
    void deleteById(Integer id);
    boolean authenticate(String username, String password);
    User login(String username, String password);
    User register(String username, String password, String fullName);
    void setPassword(String username, String newPassword);
    List<String> getRoles();

    boolean exists(Integer id);

    User getCurrentlyLoggedUser();
}
