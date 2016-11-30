package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supporter.models.Category;
import supporter.services.category.CategoryService;

import java.util.List;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String list(Model model) {
        List<Category> categories = this.categoryService.findAll(true);
        model.addAttribute("categories", categories);
        return "admin/category/list";
    }
}
