package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import supporter.models.Product;
import supporter.models.User;
import supporter.services.product.ProductService;
import supporter.services.user.UserService;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
public class SubscriptionController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/product/subscribe/{productId}")
    public String subscribe(@PathVariable Integer productId, Model model){
        if (!this.productService.exists(productId)) {
            return "redirect:/";
        }

        Product product = this.productService.findById(productId);
        User currentUser = this.userService.getCurrentLoggedUser();

        if (product.getProducer().getId().equals(currentUser.getId())) {
            return "error/403";
        }

        model.addAttribute("product", product);
        return "product/subscribe";
    }

    @PostMapping("/product/subscribe/{productId}")
    public String subscribeProcess(@PathVariable Integer productId) {
        if (!this.productService.exists(productId)) {
            return "redirect:/";
        }

        User user = this.userService.getCurrentLoggedUser();
        Product product = this.productService.findById(productId);

        user.getSupportedProducts().add(product);
        product.getSupportedUsers().add(user);
        this.productService.edit(product);
        this.userService.edit(user);

        return "redirect:/";
    }

}
