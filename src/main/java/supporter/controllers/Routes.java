package supporter.controllers;

/**
 * Created by Ivaylo on 23-Nov-16.
 */
public class Routes {
    static final String DELIMITER = "/";
    private static final String PRODUCT = "product";
    private static final String HOME = "home";
    private static final String USER = "user";

    public static final String REDIRECT_HOME = "redirect:/";
    public static final String REDIRECT_REGISTER = "redirect:/register";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String BASE_LAYOUT = "base-layout";

    public static final String VIEW = "view";

    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String PROFILE = "profile";
    public static final String HOME_INDEX = HOME + DELIMITER + "index";
    public static final String USER_REGISTER = USER + DELIMITER + REGISTER;
    public static final String USER_LOGIN = USER + DELIMITER + LOGIN;
    public static final String USER_PROFILE = USER + DELIMITER + PROFILE;
}
