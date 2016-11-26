package supporter.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import supporter.models.Role;
import supporter.models.User;
import supporter.models.binding.UserBindingModel;
import supporter.repositories.RoleRepository;
import supporter.repositories.UserRepository;
import supporter.services.role.RoleService;
import supporter.services.user.UserService;
import supporter.utils.Const;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Controller
public class UserController {

    private static final String USER_KEY = "user";

    @Autowired
    private RoleService roleService;

    @Autowired
    UserService userService;

    @GetMapping(Routes.DELIMITER + Routes.REGISTER)
    public String register(Model model) {
        model.addAttribute(Routes.VIEW, Routes.USER_REGISTER);

        return Routes.BASE_LAYOUT;
    }

    @PostMapping(Routes.DELIMITER + Routes.REGISTER)
    public String registerProcess(UserBindingModel userBindingModel){

        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){
            return Routes.REDIRECT_REGISTER;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(
                userBindingModel.getEmail(),
                userBindingModel.getFullName(),
                bCryptPasswordEncoder.encode(userBindingModel.getPassword())
        );

        Role userRole = this.roleService.findByName(Const.ROLE_USER_KEY);

        user.addRole(userRole);

        this.userService.create(user);

        return Routes.REDIRECT_LOGIN;
    }

    @GetMapping(Routes.DELIMITER + Routes.LOGIN)
    public String login(Model model){
        model.addAttribute(Routes.VIEW, Routes.USER_LOGIN);

        return Routes.BASE_LAYOUT;
    }

    @RequestMapping(value=Routes.DELIMITER + Routes.LOGOUT, method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return Routes.REDIRECT_HOME;
    }

    @GetMapping(Routes.DELIMITER + Routes.PROFILE)
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){
        User user = this.userService.getCurrentlyLoggedUser();
        model.addAttribute(USER_KEY, user);
        model.addAttribute(Routes.VIEW, Routes.USER_PROFILE);

        return Routes.BASE_LAYOUT;
    }
}
