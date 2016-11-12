package supporter.services.user;

import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
@Service
public class UserServiceStubImpl implements UserService {
//region Overridden Methods
    @Override
    public boolean authenticate(String username, String passHash) {
        return Objects.equals(username, passHash);
    }
//endregion Overridden Methods
}
