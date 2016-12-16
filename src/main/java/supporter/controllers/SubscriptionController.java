package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.Product;
import supporter.models.User;
import supporter.services.product.ProductService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import java.util.Set;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("products/subscribed")
public class SubscriptionController extends BaseController{

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/{productId}")
    public String subscribe(Model model, final @PathVariable Integer productId, final RedirectAttributes redirectAttributes){
        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/";
        }

        Product product = this.productService.findById(productId);
        User currentUser = this.userService.getCurrentLoggedUser();

        if (product.getProducer().getId().equals(currentUser.getId())) {
            return "error/403";
        }

        if (product.getSupportedUsers().contains(currentUser)) {
            model.addAttribute(Const.PRODUCT_USER_RELATION_VIEW_KEY, "related");
        }

        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        return "product/subscription/subscribe";
    }

    @PostMapping("/{productId}")
    public String subscribeProcess(final @PathVariable Integer productId, final RedirectAttributes redirectAttributes) {
        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/";
        }

        User user = this.userService.getCurrentLoggedUser();
        Product product = this.productService.findById(productId);

        user.getSupportedProducts().add(product);
        this.userService.edit(user);
        String text = DisplayedMessages.SUBSRIPTION_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(text, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
        return "redirect:/products/subscribed/list";
    }

    @GetMapping("/list")
    public String showAllSubscribedProducts(Model model){
        User user = this.userService.getCurrentLoggedUser();
        Set<Product> subscribedProducts = user.getSupportedProducts();
        model.addAttribute(Const.SUPPORTED_PRODUCTS, subscribedProducts);
        return "product/subscription/list";
    }

    @GetMapping("/view/{id}")
    public String detailedSubscription(Model model, final @PathVariable Integer id, final RedirectAttributes redirectAttributes) {
        User user = this.userService.getCurrentLoggedUser();
        Set<Product> supportedProducts = user.getSupportedProducts();
        Product product = this.productService.findById(id);
        if (!supportedProducts.contains(product)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/list";
        }

        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        return "product/subscription/details";
    }

}
