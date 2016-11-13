package supporter.forms;

import supporter.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 13-Nov-16.
 */
public class RegisterForm {


    @Size(min = ValidationRules.USERNAME_MIN_LENGTH,
            max = ValidationRules.USERNAME_MAX_LENGTH,
            message = ValidationRules.USERNAME_ERROR_MESSAGE)
    private String username;

    @NotNull
    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH,
            max = ValidationRules.PASSWORD_MAX_LENGTH)
    private String password;

    @Size(min = ValidationRules.FULL_NAME_MIN_LENGTH,
            max = ValidationRules.FULL_NAME_MAX_LENGTH)
    private String fullName;

    private User.Category userCategory;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User.Category getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(User.Category userCategory) {
        this.userCategory = userCategory;
    }
}
