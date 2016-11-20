package supporter.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import supporter.models.User;
import supporter.repositories.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Service
@Primary
public class UsersServiceJpaImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UsersServiceJpaImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findOne(id);
    }

    @Override
    public User create(User user) {

        return this.userRepository.save(user);
    }

    @Override
    public User edit(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return Objects.equals(username, password);
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
    public UserDetails loadUserByUsername(String username)
         throws UsernameNotFoundException {
            User user = userRepository.getUserByUsername(username);
            if(user == null) {
                throw new UsernameNotFoundException("Could not find user " + username);
            }
            return new CustomUserDetails(user);
    }

    @Override
    public List<String> getRoles() {
        User.Category[] rolesValues = User.Category.class.getEnumConstants();
        return Arrays.stream(rolesValues)
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    private class CustomUserDetails extends User implements UserDetails{

        CustomUserDetails(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            String[] roles = (String[]) getRoles().toArray();
            return AuthorityUtils.createAuthorityList(roles);
        }

        @Override
        public String getPassword() {
            return super.getPasswordHash();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
