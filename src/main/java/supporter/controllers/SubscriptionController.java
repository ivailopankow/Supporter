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
import supporter.models.Ticket;
import supporter.models.User;
import supporter.services.product.ProductService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String subscribe(HttpServletRequest request, Model model, final @PathVariable Integer productId, final RedirectAttributes redirectAttributes){
        super.loadCategories(model);
        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/";
        }

        Product product = this.productService.findById(productId);
        User currentUser = this.userService.getCurrentLoggedUser();

        if (product.getProducer().getId().equals(currentUser.getId())) {
            model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
            model.addAttribute(Const.USER_VIEW_KEY, currentUser);
            String redirect = request.getContextPath() + "/product/" + product.getId();
            return "redirect:" + redirect;
        }

        if (product.getSupportedUsers().contains(currentUser)) {
            model.addAttribute(Const.PRODUCT_USER_RELATION_VIEW_KEY, "related");
        } else {
            model.addAttribute("userProductNotRelated", "not related");
        }

        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        return "product/subscription/subscribe";
    }

    @PostMapping("/{productId}")
    public String subscribeProcess(final @PathVariable Integer productId, final RedirectAttributes redirectAttributes, final Model model) {
        super.loadCategories(model);
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
        super.loadCategories(model);
        User user = this.userService.getCurrentLoggedUser();
        Set<Product> subscribedProducts = user.getSupportedProducts();
        List<Product> productList = super.getSortedProducts(subscribedProducts);
        model.addAttribute(Const.SUPPORTED_PRODUCTS, productList);
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
        List<Ticket> ticketList = product.getTickets().stream()
                .filter(t -> t.getAuthor() == user)
                .collect(Collectors.toList());
        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        model.addAttribute(Const.TICKETS_VIEW_KEY, ticketList);
        return "product/subscription/details";
    }

}
