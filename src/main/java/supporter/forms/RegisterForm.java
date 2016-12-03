package supporter.forms;

import supporter.utils.ValidationRules;

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

    @Size(min = ValidationRules.FIRST_NAME_MIN_LENGTH,
            max = ValidationRules.FIRST_NAME_MAX_LENGTH)
    private String firstName;

    @Size(min = ValidationRules.LAST_NAME_MIN_LENGTH,
            max = ValidationRules.LAST_NAME_MAX_LENGTH)

    public String lastName;


    public String repeatedPassword;

    public String userCategory;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
}
