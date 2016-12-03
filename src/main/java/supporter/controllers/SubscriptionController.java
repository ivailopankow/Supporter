package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supporter.models.Product;
import supporter.models.User;
import supporter.services.product.ProductService;
import supporter.services.user.UserService;

import java.util.Objects;
import java.util.Set;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("products/subscribed")
public class SubscriptionController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/{productId}")
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
        return "product/subscription/subscribe";
    }

    @PostMapping("/{productId}")
    public String subscribeProcess(@PathVariable Integer productId) {
        if (!this.productService.exists(productId)) {
            return "redirect:/";
        }

        User user = this.userService.getCurrentLoggedUser();
        Product product = this.productService.findById(productId);

        user.getSupportedProducts().add(product);
        this.userService.edit(user);

        return "redirect:/products/subscribed/list";
    }

    @GetMapping("/list")
    public String showAllSubscribedProducts(Model model){
        User user = this.userService.getCurrentLoggedUser();
        Set<Product> subscribedProducts = user.getSupportedProducts();
        model.addAttribute("subscribedProducts", subscribedProducts);
        return "product/subscription/list";
    }

    @GetMapping("/view/{id}")
    public String detailedSubscription(Model model, @PathVariable Integer id) {
        User user = this.userService.getCurrentLoggedUser();
        Set<Product> supportedProducts = user.getSupportedProducts();
        if (! supportedProducts.contains(id)) {
            // TODO: 03-Dec-16 implement error handling
        }
        Product product = supportedProducts.stream()
                .filter( p -> Objects.equals(p.getId(), id))
                .findFirst()
                .get();
        model.addAttribute("product", product);
        return "product/subscription/details";
    }

}
