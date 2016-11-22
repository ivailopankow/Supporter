package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import supporter.models.Product;
import supporter.models.User;
import supporter.models.binding.ProductBindingModel;
import supporter.repositories.ProductRepository;
import supporter.repositories.UserRepository;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Controller
public class ProductController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/product/create")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("unused")
    public String createProduct(Model model) {
        model.addAttribute("view", "product/create");
        return "base-layout";
    }

    @PostMapping("/product/create")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("unused")
    public String createProductProcess(ProductBindingModel productBindingModel) {
        UserDetails user = (UserDetails) SecurityContextHolder
                                        .getContext()
                                        .getAuthentication()
                                        .getPrincipal();

        User userEntity = userRepository.findByEmail(user.getUsername());

        Product product = new Product(
                productBindingModel.getTitle(),
                productBindingModel.getContent(),
                userEntity
        );

        productRepository.saveAndFlush(product);

        return "redirect:/";
    }
}
