package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.exceptions.category.CategoryCreationException;
import supporter.models.Category;
import supporter.models.binding.CategoryBindingModel;
import supporter.services.category.CategoryService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
@Controller
@RequestMapping("/admin/categories")
public class CategoryController extends BaseController{
    @Autowired
    private CategoryService categoryService;
    @ModelAttribute(Const.BINDING_MODEL_CATEGORY)
    public CategoryBindingModel newCategoryBindingModel() {
        return new CategoryBindingModel();
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Category> categories = this.categoryService.findAll(true);
        model.addAttribute(Const.CATEGORIES_VIEW_KEY, categories);
        return "admin/category/list";
    }

    @GetMapping("/create")
    public String create() {
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String createProcess(@Valid @ModelAttribute(Const.BINDING_MODEL_CATEGORY) final CategoryBindingModel categoryBindingModel,
                                final BindingResult bindingResult,
                                final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String messageText = DisplayedMessages.ERROR_IN_FORM;
            NotificationMessage message = super.generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_RESULT_CATEGORY, bindingResult);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_CATEGORY, categoryBindingModel);
            redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
            return "redirect:/admin/categories/create";
        }
        try {
            this.categoryService.create(categoryBindingModel);
        } catch (CategoryCreationException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }
        String text = DisplayedMessages.CREATE_CATEGORY_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/admin/categories/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, final @PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        if (!this.categoryService.exists(id)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/categories/create";
        }
        Category category = this.categoryService.findById(id);
        model.addAttribute(Const.CATEGORY_VIEW_KEY, category);
        return "admin/category/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(final @PathVariable Integer id, final CategoryBindingModel categoryBindingModel,
                              final RedirectAttributes redirectAttributes) {
        if (!this.categoryService.exists(id)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/categories/create";
        }
        try {
            this.categoryService.edit(id, categoryBindingModel);
        } catch (CategoryCreationException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
        }

        String text = DisplayedMessages.EDIT_CATEGORY;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/admin/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, final @PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        if (!this.categoryService.exists(id)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/categories/create";
        }
        Category category = this.categoryService.findById(id);
        model.addAttribute(Const.CATEGORY_VIEW_KEY, category);

        return "admin/category/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(final @PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        if (!this.categoryService.exists(id)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/admin/categories/create";
        }
        this.categoryService.deleteById(id);

        String text = DisplayedMessages.DELETE_CATEGORY;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);

        return "redirect:/admin/categories/list";
    }
}
