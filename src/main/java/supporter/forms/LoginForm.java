package supporter.forms;


import supporter.utils.ValidationRules;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public class LoginForm {
//region Fields
    @Size(min = ValidationRules.USERNAME_MIN_LENGTH,
            max = ValidationRules.USERNAME_MAX_LENGTH,
            message = ValidationRules.USERNAME_ERROR_MESSAGE)
    private String username;

    @NotNull
    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH,
            max = ValidationRules.USERNAME_MAX_LENGTH)
    private String password;
//endregion Fields

//region Properties
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
//endregion Properties
}

