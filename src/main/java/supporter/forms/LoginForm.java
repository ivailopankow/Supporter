package supporter.forms;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public class LoginForm {
//region Fields
    @Size(min = 2, max = 30, message = "Username size should be in the range [2...30]")
    private String username;

    @NotNull
    @Size(min = 1, max = 50)
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

