package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import supporter.models.Category;
import supporter.models.Product;
import supporter.services.category.CategoryService;
import supporter.utils.Const;

import java.util.List;
import java.util.Set;

/**
 * Created by Ivaylo on 30-Oct-16.
 */
@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll(false);
        model.addAttribute(Const.CATEGORIES_VIEW_KEY, categories);
        return "home/index";
    }

    @GetMapping("/category/{categoryId}")
    public String showCategoryProducts(Model model, @PathVariable Integer categoryId) {
        if (!this.categoryService.exists(categoryId)) {
            return "redirect:/";
        }

        Category category = this.categoryService.findById(categoryId);
        Set<Product> products = category.getProducts();
        model.addAttribute(Const.CATEGORY_VIEW_KEY, category);
        model.addAttribute(Const.PRODUCTS_VIEW_KEY, products);
        return "home/category-products-list";
    }

    @RequestMapping("/error/403")
    public String accessDenied() {
        return "error/403";
    }
}
