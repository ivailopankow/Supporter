package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

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

    @GetMapping("/product/{productId}")
    public String details(@PathVariable int productId,
                          Model model){
        if (!this.productRepository.exists(productId)){
            return "redirect:/product/create";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userRepository.findByEmail(principal.getUsername());
            model.addAttribute("user", entityUser);
        }
        Product product = productRepository.findOne(productId);

        model.addAttribute("product", product);
        model.addAttribute("view", "product/details");

        return "base-layout";
    }

    @GetMapping("/product/edit/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable int productId,
                       Model model) {

        if (!this.productRepository.exists(productId)){
            return "redirect:/product/create";
        }

        Product product = this.productRepository.findOne(productId);
        model.addAttribute("product", product);
        model.addAttribute("view", "product/edit");
        return "base-layout";
    }

    @PostMapping("/product/edit/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable int productId,
                              ProductBindingModel bindingModel) {

        if (!this.productRepository.exists(productId)){
            return "redirect:/product/create";
        }

        Product product = this.productRepository.findOne(productId);
        product.setTitle(bindingModel.getTitle());
        product.setContent(bindingModel.getContent());

        this.productRepository.saveAndFlush(product);
        return "redirect:/";
    }

    @GetMapping("product/delete/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer productId) {
        if (!this.productRepository.exists(productId)){
            return "redirect:/product/create";
        }

        Product product = this.productRepository.findOne(productId);
        model.addAttribute("product", product);
        model.addAttribute("view", "product/delete");
        return "base-layout";
    }

    @PostMapping("product/delete/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer productId){
        if (!this.productRepository.exists(productId)){
            return "redirect:/product/create";
        }

        this.productRepository.delete(productId);
        return "redirect:/";
    }


}
