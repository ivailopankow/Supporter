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
public class UsersServiceJpaImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findOne(id);
    }

    @Override
    public User findByEmail(String username) {
        return this.userRepository.findByEmail(username);
    }

    @Override
    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User edit(User user) {
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteById(Integer id) {
        this.userRepository.delete(id);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    @Override
    public User register(String username, String password, String fullName) {
        return null;
    }

    @Override
    public void setPassword(String username, String newPassword) {

    }

    @Override
    public List<String> getRoles() {
        return null;
    }

    @Override
    public boolean exists(Integer id) {
        return this.userRepository.exists(id);
    }
}
