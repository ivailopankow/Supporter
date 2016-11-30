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
import supporter.models.Category;
import supporter.models.Product;
import supporter.models.User;
import supporter.models.binding.ProductBindingModel;
import supporter.services.category.CategoryService;
import supporter.services.product.ProductService;
import supporter.services.user.UserService;

import java.util.List;
import java.util.Set;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Controller
public class ProductController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/product/create")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("unused")
    public String create(Model model) {
        List<Category> categories = this.categoryService.findAll(true);
        model.addAttribute("categories", categories);
        return "product/create";
    }

    @PostMapping("/product/create")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("unused")
    public String createProcess(ProductBindingModel productBindingModel) {
        User userEntity = this.userService.getCurrentLoggedUser();
        Category category = this.categoryService.findById(productBindingModel.getCategoryId());

        Product product = new Product(
                productBindingModel.getTitle(),
                productBindingModel.getContent(),
                userEntity,
                category
        );

        productService.create(product);

        return "redirect:/";
    }

    @GetMapping("/product/{productId}")
    public String details(@PathVariable int productId,
                          Model model){
        if (!this.productService.exists(productId)){
            return "redirect:/product/create";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userService.findByEmail(principal.getUsername());
            model.addAttribute("user", entityUser);
        }
        Product product = productService.findById(productId);

        model.addAttribute("product", product);

        return "product/details";
    }

    @GetMapping("/product/edit/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable int productId,
                       Model model) {

        if (!this.productService.exists(productId)){
            return "redirect:/product/create";
        }

        Product product = this.productService.findById(productId);
        model.addAttribute("product", product);
        return "product/edit";
    }

    @PostMapping("/product/edit/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable int productId,
                              ProductBindingModel bindingModel) {

        if (!this.productService.exists(productId)){
            return "redirect:/product/create";
        }

        Product product = this.productService.findById(productId);
        product.setTitle(bindingModel.getTitle());
        product.setContent(bindingModel.getContent());

        this.productService.edit(product);
        return "redirect:/";
    }

    @GetMapping("product/delete/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer productId) {
        if (!this.productService.exists(productId)){
            return "redirect:/product/create";
        }

        Product product = this.productService.findById(productId);
        model.addAttribute("product", product);
        return "product/delete";
    }

    @PostMapping("product/delete/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer productId){
        if (!this.productService.exists(productId)){
            return "redirect:/product/create";
        }

        this.productService.deleteById(productId);
        return "redirect:/";
    }
}
