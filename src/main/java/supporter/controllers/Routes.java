package supporter.controllers;

/**
 * Created by Ivaylo on 23-Nov-16.
 */
class Routes {
    static final String DELIMITER = "/";
    private static final String PRODUCT = "product";
    private static final String HOME = "home";
    private static final String USER = "user";

    static final String REDIRECT_HOME = "redirect:/";
    static final String REDIRECT_REGISTER = "redirect:/register";
    static final String REDIRECT_LOGIN = "redirect:/login";
    static final String BASE_LAYOUT = "base-layout";

    static final String VIEW = "view";

    static final String REGISTER = "register";
    static final String LOGIN = "login";
    static final String LOGOUT = "logout";
    static final String PROFILE = "profile";
    static final String HOME_INDEX = HOME + DELIMITER + "index";
    static final String USER_REGISTER = USER + DELIMITER + REGISTER;
    static final String USER_LOGIN = USER + DELIMITER + LOGIN;
    static final String USER_PROFILE = USER + DELIMITER + PROFILE;
}
