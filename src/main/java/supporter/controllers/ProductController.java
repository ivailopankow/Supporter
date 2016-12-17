package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.*;
import supporter.models.binding.ProductBindingModel;
import supporter.services.category.CategoryService;
import supporter.services.product.ProductService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Controller
public class ProductController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute(Const.BINDING_MODEL_CREATE_PRODUCT)
    public ProductBindingModel newProductBindingModel() {
        return new ProductBindingModel();
    }


    @GetMapping("/product/create")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("unused")
    public String create(Model model) {
        super.loadCategories(model);
        List<Category> categories = this.categoryService.findAll(true);
        model.addAttribute(Const.CATEGORIES_VIEW_KEY, categories);
        return "product/create";
    }

    @PostMapping("/product/create")
    @PreAuthorize("isAuthenticated()")
    @SuppressWarnings("unused")
    public String createProcess(@Valid @ModelAttribute(Const.BINDING_MODEL_CREATE_PRODUCT) final ProductBindingModel productBindingModel,
                                final BindingResult bindingResult,
                                final RedirectAttributes redirectAttributes, final Model model) {

        super.loadCategories(model);
        if (bindingResult.hasErrors()) {
            // TODO: 03-Dec-16 extract this logic in BaseController
            String messageText = DisplayedMessages.ERROR_IN_FORM;
            NotificationMessage message = super.generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_RESULT_PRODUCT, bindingResult);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_CREATE_PRODUCT, productBindingModel);
            redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
            return "redirect:/product/create";
        }

        User userEntity = this.userService.getCurrentLoggedUser();
        Category category = this.categoryService.findById(productBindingModel.getCategoryId());

        Product product = new Product(
                productBindingModel.getTitle(),
                productBindingModel.getContent(),
                userEntity,
                category
        );

        String notificationMessage = DisplayedMessages.CREATE_PRODUCT_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(notificationMessage, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);

        productService.create(product);

        return "redirect:/";
    }

    @GetMapping("/product/{productId}")
    public String details(@PathVariable int productId, Model model, RedirectAttributes redirectAttributes){
        super.loadCategories(model);
        if (!this.productService.exists(productId)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/product/create";
        }

        Product product = productService.findById(productId);
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userService.findByEmail(principal.getUsername());
            model.addAttribute(Const.USER_VIEW_KEY, entityUser);
            if (!product.getSupportedUsers().contains(entityUser)) {
                model.addAttribute("showSubscribe", "subscribe");
            }

            if (product.getProducer() == entityUser) {
                model.addAttribute(Const.USER_VIEW_KEY, entityUser);
            }

        } else {
            model.addAttribute("showSubscribe", "subscribe");
        }


        if (alreadyDeleted(product, redirectAttributes)) {
            return "redirect:/product/create";
        }

        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);

        return "product/details";
    }

    @GetMapping("/product/edit/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable int productId, Model model, RedirectAttributes redirectAttributes) {
        super.loadCategories(model);

        if (!this.productService.exists(productId)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/product/create";
        }

        Product product = this.productService.findById(productId);
        List<Category> categories = this.categoryService.findAll(true);
        if (alreadyDeleted(product, redirectAttributes)){
            return "redirect:/product/create";
        }
        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        model.addAttribute(Const.CATEGORIES_VIEW_KEY, categories);
        return "product/edit";
    }

    @PostMapping("/product/edit/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(final @PathVariable Integer productId,
                              final ProductBindingModel bindingModel,
                              final RedirectAttributes redirectAttributes,
                              final Model model) {

        super.loadCategories(model);
        if (!this.productService.exists(productId)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/product/create";
        }

        Category category = this.categoryService.findById(bindingModel.getCategoryId());
        Product product = this.productService.findById(productId);
        if (alreadyDeleted(product, redirectAttributes)){
            return "redirect:/product/create";
        }
        product.setTitle(bindingModel.getTitle());
        product.setContent(bindingModel.getContent());
        product.setCategory(category);

        this.productService.edit(product);
        String messageText = DisplayedMessages.EDIT_PRODUCT;
        NotificationMessage message =  super.generateNotificationMessage(messageText, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/products/supporting/list";
    }

    @GetMapping("product/delete/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, final @PathVariable Integer productId, final RedirectAttributes redirectAttributes) {
        super.loadCategories(model);
        if (!this.productService.exists(productId)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/product/create";
        }

        Product product = this.productService.findById(productId);

        if (alreadyDeleted(product, redirectAttributes)){
            return "redirect:/product/create";
        }

        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        return "product/delete";
    }

    @PostMapping("product/delete/{productId}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(final @PathVariable Integer productId, final RedirectAttributes redirectAttributes, final Model model){
        super.loadCategories(model);
        if (!this.productService.exists(productId)){
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/product/create";
        }

        Product deleteCandidate  = this.productService.findById(productId);
        Set<Ticket> tickets = deleteCandidate.getTickets();

        for (Ticket ticket : tickets) {
            ticket.setDeleted(true);
            for (Comment comment : ticket.getComments()) {
                comment.setDeleted(true);
            }
        }

        deleteCandidate.setDeleted(true);
        this.productService.edit(deleteCandidate);

        String text = DisplayedMessages.DELETE_PRODUCT;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/";
    }

    private boolean alreadyDeleted(Product product, RedirectAttributes redirectAttributes) {
        if (product.isDeleted()) {
            super.showNonExistingResourceError(redirectAttributes);
            return true;
        }
        return false;
    }

    private boolean isRelated(Product product, final Model model) {

        if ((SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return false;
        } else {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User entityUser = this.userService.findByEmail(principal.getUsername());
            model.addAttribute(Const.USER_VIEW_KEY, entityUser);
            return product.getSupportedUsers().contains(entityUser) || product.getProducer() == entityUser;
        }
    }
}
