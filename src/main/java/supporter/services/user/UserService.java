package supporter.services.user;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public interface UserService {
//region Abstract Methods
    boolean authenticate(String username, String passHash);
//endregion Abstract Methods
}
