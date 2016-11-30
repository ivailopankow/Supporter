package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        if (!this.categoryService.exists(id)) {
            return "redirect:/admin/categories/create";
        }
        Category category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, CategoryBindingModel categoryBindingModel) {
        if (!this.categoryService.exists(id)) {
            return "redirect:/admin/categories/create";
        }
        try {
            this.categoryService.edit(id, categoryBindingModel);
        } catch (CategoryCreationException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
        return "redirect:/admin/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.categoryService.exists(id)) {
            return "redirect:/admin/categories/create";
        }
        Category category = this.categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.categoryService.exists(id)) {
            return "redirect:/admin/categories/create";
        }
        this.categoryService.deleteById(id);
        return "redirect:/admin/categories/list";
    }
}
