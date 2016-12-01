package supporter.forms;

/**
 * Created by Ivaylo on 13-Nov-16.
 */
public class ValidationRules {

    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 30;
    public static final int PASSWORD_MIN_LENGTH = 2;
    public static final int PASSWORD_MAX_LENGTH = 50;
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int FIRST_NAME_MAX_LENGTH = 10;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 10;

   public static final String USERNAME_ERROR_MESSAGE = "Username size should be in the range [" +
                                                USERNAME_MIN_LENGTH + "..." +  USERNAME_MAX_LENGTH + "]";


}
