package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import supporter.exceptions.category.CategoryCreationException;
import supporter.models.Category;
import supporter.models.binding.CategoryBindingModel;
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

    @GetMapping("/list")
    public String list(Model model) {
        List<Category> categories = this.categoryService.findAll(true);
        model.addAttribute("categories", categories);
        return "admin/category/list";
    }

    @GetMapping("/create")
    public String create() {
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel) {
        try {
            this.categoryService.create(categoryBindingModel);
        } catch (CategoryCreationException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
        return "redirect:/admin/categories/list";
    }
}
