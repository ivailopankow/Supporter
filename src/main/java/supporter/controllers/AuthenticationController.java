//package supporter.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import supporter.forms.LoginForm;
//import supporter.forms.RegisterForm;
//import supporter.models.User;
//import supporter.services.notification.NotificationService;
//import supporter.services.user.UserService;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * Created by Ivaylo on 12-Nov-16.
// */
//@Controller
//public class AuthenticationController {
//
//    @SuppressWarnings("WeakerAccess")
//    static final String USERS_LOGIN_PAGE = "users/login";
//    @SuppressWarnings("WeakerAccess")
//    static final String USERS_REGISTER_PAGE = "users/register";
//    private static final String REDIRECT_HOME = "redirect:/";
//    private static final String DELIMITER = "/";
//    private static final String ROLES = "userRoles";
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    NotificationService notificationService;
//
//    @RequestMapping(DELIMITER + USERS_LOGIN_PAGE)
//    public String showLoginPage(@SuppressWarnings("UnusedParameters") LoginForm loginForm) {
//        return USERS_LOGIN_PAGE;
//    }
//
//    @RequestMapping(value = DELIMITER + USERS_LOGIN_PAGE, method = RequestMethod.POST)
//    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            notificationService.addErrorMessage(ErrorMessages.ERROR_IN_FORM);
//            return USERS_LOGIN_PAGE;
//        }
//
//        if (!userService.authenticate(
//                loginForm.getUsername(), loginForm.getPassword())) {
//            notificationService.addErrorMessage("Invalid login!");
//            return USERS_LOGIN_PAGE;
//        }
//
//        notificationService.addInfoMessage("Login successful");
//        return REDIRECT_HOME;
//    }
//
//    @RequestMapping(DELIMITER + USERS_REGISTER_PAGE)
//    public String showRegisterPage(@SuppressWarnings("UnusedParameters") RegisterForm registerForm, Model model) {
//        List<String> allRoles = userService.getRoles();
//        model.addAttribute(ROLES, allRoles);
//        return USERS_REGISTER_PAGE;
//    }
//
//    @RequestMapping(value = DELIMITER + USERS_REGISTER_PAGE, method = RequestMethod.POST)
//    public String registerPage(@Valid RegisterForm registerForm, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            notificationService.addErrorMessage(ErrorMessages.ERROR_IN_FORM);
//            return USERS_REGISTER_PAGE;
//        }
//
//        String password = registerForm.getPassword();
//        String repeatedPassword = registerForm.getRepeatedPassword();
//
//        if (!password.equals(repeatedPassword)) {
//            notificationService.addErrorMessage(ErrorMessages.PASSWORD_MISMATCH);
//            return USERS_REGISTER_PAGE;
//        }
//
//        User user = createUser(registerForm);
//
//        userService.create(user);
//
//        notificationService.addInfoMessage("Register successful");
//        return REDIRECT_HOME;
//    }
//
//    private User createUser(RegisterForm registerForm) {
//        User.Category category = User.Category.valueOf(registerForm.getUserCategory());
//        String fullName = registerForm.getFirstName() + " " + registerForm.getLastName();
//        String password = registerForm.getPassword();
//        String username = registerForm.getUsername();
//
//        // TODO: 18-Nov-16 Hash the password here
//        User user = new User(category, username, password, fullName);
//        return user;
//    }
//
//}
