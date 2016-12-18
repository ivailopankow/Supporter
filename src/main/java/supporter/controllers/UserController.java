package supporter.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.Product;
import supporter.models.Role;
import supporter.models.User;
import supporter.models.binding.UserBindingModel;
import supporter.services.role.RoleService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Controller
public class UserController extends BaseController{

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @ModelAttribute(Const.BINDING_MODEL_REGISTER)
    public UserBindingModel newUserBindingModel() {
        return new UserBindingModel();
    }

    private Logger logger;

    public UserController() {
        this.logger = Logger.getLogger(UserController.class.getSimpleName());
    }

    @GetMapping("/register")
    public String register(final Model model) {
        super.loadCategories(model);
        return "user/register";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid @ModelAttribute(Const.BINDING_MODEL_REGISTER) final UserBindingModel userBindingModel,
                                  final BindingResult bindingResult,
                                  final RedirectAttributes redirectAttributes,
                                  final Model model){

        super.loadCategories(model);
        if (bindingResult.hasErrors()) {
            String messageText = DisplayedMessages.ERROR_IN_FORM;
            NotificationMessage message = super.generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_RESULT_REGISTER, bindingResult);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_REGISTER, userBindingModel);
            redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
            return "redirect:/register";
        }
        if(!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())){

            String messageText = DisplayedMessages.PASSWORD_MISMATCH;
            NotificationMessage message = super.generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerForm", bindingResult);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_REGISTER, userBindingModel);
            redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
            return "redirect:/register";
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
        String text = DisplayedMessages.USER_REGISTER_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(final Model model){
        super.loadCategories(model);
        return "user/login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response,
                              RedirectAttributes redirectAttributes,
                              final Model model) {
        super.loadCategories(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        String text = DisplayedMessages.LOGOUT_SUCCESSFUL;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profilePage(Model model){
        super.loadCategories(model);
        User user = this.userService.getCurrentLoggedUser();
        model.addAttribute(Const.USER_VIEW_KEY, user);
        return "user/profile";
    }

    @GetMapping("/products/supporting/list")
    @PreAuthorize("isAuthenticated()")
    public String listProducerProducts(Model model) {
        super.loadCategories(model);
        User loggedUser = this.userService.getCurrentLoggedUser();
        Set<Product> userProducts = loggedUser.getProducts();
        List<Product> productList = super.getSortedProducts(userProducts);
        productList = productList.stream()
                .filter(p -> !p.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute(Const.PRODUCTS_VIEW_KEY, productList);

        return "product/user-list";
    }
}
