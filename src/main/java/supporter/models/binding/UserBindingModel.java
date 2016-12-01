package supporter.models.binding;

import supporter.forms.ValidationRules;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
public class UserBindingModel {
    @Size(min = ValidationRules.USERNAME_MIN_LENGTH,
            max = ValidationRules.USERNAME_MAX_LENGTH,
            message = ValidationRules.USERNAME_ERROR_MESSAGE)
    private String email;

    @NotNull
    private String fullName;

    @NotNull
    @Size(min = ValidationRules.PASSWORD_MIN_LENGTH,
            max = ValidationRules.PASSWORD_MAX_LENGTH)
    private String password;

    @NotNull
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
