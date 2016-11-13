package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import supporter.forms.LoginForm;
import supporter.forms.RegisterForm;
import supporter.services.NotificationService;
import supporter.services.user.UserService;

import javax.validation.Valid;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
@Controller
public class AuthenticationController {

    @SuppressWarnings("WeakerAccess")
    static final String USERS_LOGIN_PAGE = "users/login";
    @SuppressWarnings("WeakerAccess")
    static final String USERS_REGISTER_PAGE = "users/register";
    private static final String REDIRECT_HOME = "redirect:/";
    private static final String DELIMITER = "/";
    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @RequestMapping(DELIMITER + USERS_LOGIN_PAGE)
    public String showLoginPage(LoginForm loginForm) {
        return USERS_LOGIN_PAGE;
    }

    @RequestMapping(value = DELIMITER + USERS_LOGIN_PAGE, method = RequestMethod.POST)
    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notificationService.addErrorMessage(ErrorMessages.ERROR_IN_FORM);
            return USERS_LOGIN_PAGE;
        }

        if (!userService.authenticate(
                loginForm.getUsername(), loginForm.getPassword())) {
            notificationService.addErrorMessage("Invalid login!");
            return USERS_LOGIN_PAGE;
        }

        notificationService.addInfoMessage("Login successful");
        return REDIRECT_HOME;
    }

    @RequestMapping(DELIMITER + USERS_REGISTER_PAGE)
    public String showRegisterPage(RegisterForm regForm) {
        return USERS_REGISTER_PAGE;
    }

}
