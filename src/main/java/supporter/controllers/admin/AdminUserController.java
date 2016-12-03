package supporter.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.controllers.BaseController;
import supporter.models.Product;
import supporter.models.Role;
import supporter.models.User;
import supporter.models.binding.EditUserBindingModel;
import supporter.services.product.ProductService;
import supporter.services.role.RoleService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivaylo on 25-Nov-16.
 */
@Controller
@RequestMapping("admin/users")
public class AdminUserController extends BaseController{

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    RoleService roleService;

    @GetMapping("/list")
    public String listUsers(Model model){
        List<User> users = this.userService.findAll();
        model.addAttribute(Const.USERS_VIEW_KEY, users);

        return "admin/user/list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, final @PathVariable Integer id, final RedirectAttributes redirectAttributes){
        if (!this.userService.exists(id)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/users/list";
        }

        User user = this.userService.findById(id);
        List<Role> roles = this.roleService.findAll();
        model.addAttribute(Const.USER_VIEW_KEY, user);
        model.addAttribute(Const.ROLES_VIEW_KEY, roles);

        return "admin/user/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUserProcess(final @PathVariable Integer id, final EditUserBindingModel userBindingModel,
                                  final RedirectAttributes redirectAttributes){
        if (!this.userService.exists(id)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/users/list";
        }
        User user = this.userService.findById(id);

        user.setEmail(userBindingModel.getEmail());
        user.setFullName(userBindingModel.getFullName());

        //get binding password
        String enteredPass = userBindingModel.getPassword();
        String confirmedPass = userBindingModel.getConfirmPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if ((!enteredPass.isEmpty() && !confirmedPass.isEmpty())
                && enteredPass.equals(confirmedPass)){
            user.setPassword(encoder.encode(enteredPass));
        }

        //get binding roles
        Set<Role> roles = new HashSet<>();
        for (Integer roleId : userBindingModel.getRoles()) {
            Role editedRole = this.roleService.findById(roleId);
            roles.add(editedRole);
        }
        user.setRoles(roles);

        this.userService.edit(user);
        String text = DisplayedMessages.EDIT_USER;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);

        return "redirect:/admin/users/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, final @PathVariable Integer id, final RedirectAttributes redirectAttributes){
        if (!this.userService.exists(id)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/users/list";
        }

        User user = this.userService.findById(id);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping("delete/{id}")
    public String deleteProcess(final @PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        if (!this.userService.exists(id)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/users/list";
        }
        User user = this.userService.findById(id);
        for (Product product : user.getProducts()) {
            this.productService.deleteById(product.getId());
        }
        this.userService.deleteById(id);
        String text = DisplayedMessages.DELETE_USER;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);

        return "redirect:/admin/users/list";
    }
}
