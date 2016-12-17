package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.Product;
import supporter.models.Ticket;
import supporter.models.User;
import supporter.models.binding.TicketBindingModel;
import supporter.services.product.ProductService;
import supporter.services.ticket.TicketService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.validation.Valid;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Controller
@RequestMapping("products/subscribed/tickets")
@PreAuthorize("isAuthenticated()")
public class TicketsController extends  BaseController{

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    private Integer currentProductId = -1;

    @ModelAttribute(Const.BINDING_MODEL_CREATE_TICKET)
    public TicketBindingModel newTicketBindingModel() {
        return new TicketBindingModel();
    }

    @GetMapping("/create/{productId}")
    public String create(final @PathVariable Integer productId,
                         final RedirectAttributes redirectAttributes,
                         @ModelAttribute(Const.BINDING_MODEL_CREATE_TICKET) final TicketBindingModel ticketBindingModel,
                         Model model) {
        super.loadCategories(model);
        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/list";
        }
        this.currentProductId = productId;
        ticketBindingModel.setProductId(productId);
        model.addAttribute(Const.BINDING_MODEL_CREATE_TICKET, ticketBindingModel);
        return "product/ticket/create";
    }

    @PostMapping("/create/{productId}")
    public String createProcess(@Valid @ModelAttribute(Const.BINDING_MODEL_CREATE_TICKET) final TicketBindingModel ticketBindingModel,
                                final BindingResult bindingResult,
                                final RedirectAttributes redirectAttributes,
                                @PathVariable Integer productId,
                                final Model model) {

        super.loadCategories(model);
        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/list";
        }

        if (!this.currentProductId.equals(productId) || this.currentProductId.equals(-1)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + currentProductId;
        }

        if (bindingResult.hasErrors()) {
            String messageText = DisplayedMessages.ERROR_IN_FORM;
            NotificationMessage message = super.generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_RESULT_TICKET, bindingResult);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_CREATE_TICKET, ticketBindingModel);
            redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
            return "redirect:/products/subscribed/tickets/create/" + productId;
        }
        Product currentProduct = this.productService.findById(productId);
        User user = this.userService.getCurrentLoggedUser();
        Ticket ticket = new Ticket(ticketBindingModel.getTitle(), ticketBindingModel.getContent(), user, currentProduct);
        this.ticketService.create(ticket);

        String notificationMessage = DisplayedMessages.CREATE_TICKET_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(notificationMessage, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);

        return "redirect:/products/subscribed/view/" + productId +"/" + ticket.getId();
    }

    @GetMapping("/view/{productId}/{id}")
    public String details (final @PathVariable Integer productId,
                           final @PathVariable Long id,
                           final RedirectAttributes redirectAttributes,
                           final Model model) {
        super.loadCategories(model);
        if (!this.ticketService.exists(id) || !this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + currentProductId;
        }

        Product product = this.productService.findById(productId);
        Ticket ticket = this.ticketService.findById(id);
        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        model.addAttribute(Const.TICKET_VIEW_KEY, ticket);
        return "product/ticket/details";
    }
}
