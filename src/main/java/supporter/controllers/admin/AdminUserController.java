package supporter.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supporter.controllers.Routes;
import supporter.models.Product;
import supporter.models.Role;
import supporter.models.User;
import supporter.models.binding.EditUserBindingModel;
import supporter.repositories.ProductRepository;
import supporter.repositories.RoleRepository;
import supporter.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivaylo on 25-Nov-16.
 */
@Controller
@RequestMapping("admin/users")
public class AdminUserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String listUsers(Model model){
        List<User> users = this.userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute(Routes.VIEW, "admin/user/list");

        return Routes.BASE_LAYOUT;
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable Integer id){
        if (!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);
        List<Role> roles = this.roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return Routes.BASE_LAYOUT;
    }

    @PostMapping("/edit/{id}")
    public String editUserProcess(@PathVariable Integer id, EditUserBindingModel userBindingModel){
        if (!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }
        User user = this.userRepository.findOne(id);

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
            Role editedRole = this.roleRepository.findOne(roleId);
            roles.add(editedRole);
        }
        user.setRoles(roles);

        this.userRepository.saveAndFlush(user);
        return "redirect:/admin/users/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        if (!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");

        return Routes.BASE_LAYOUT;
    }

    @PostMapping("delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.userRepository.exists(id)){
            return "redirect:/admin/users/";
        }
        User user = this.userRepository.findOne(id);
        for (Product product : user.getProducts()) {
            this.productRepository.delete(product.getId());
        }
        this.userRepository.delete(id);
        return "redirect:/admin/users/";
    }
}
