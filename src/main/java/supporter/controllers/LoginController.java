package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import supporter.forms.LoginForm;
import supporter.services.NotificationService;
import supporter.services.user.UserService;

import javax.validation.Valid;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
@Controller
public class LoginController {

    private static final String USERS_LOGIN_PAGE = "users/login";
    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @RequestMapping("/users/login")
    public String showLoginPage(LoginForm loginForm) {
        return USERS_LOGIN_PAGE;
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notificationService.addErrorMessage("Please fill the form correctly!");
            return "users/login";
        }

        if (!userService.authenticate(
                loginForm.getUsername(), loginForm.getPassword())) {
            notificationService.addErrorMessage("Invalid login!");
            return "users/login";
        }

        notificationService.addInfoMessage("Login successful");
        return "redirect:/";
    }

}
