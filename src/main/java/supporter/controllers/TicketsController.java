package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.Comment;
import supporter.models.Product;
import supporter.models.Ticket;
import supporter.models.User;
import supporter.models.binding.TicketBindingModel;
import supporter.services.comment.CommentService;
import supporter.services.product.ProductService;
import supporter.services.ticket.TicketService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Controller
@RequestMapping("products/subscribed/tickets")
@PreAuthorize("isAuthenticated()")
public class TicketsController extends BaseController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
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

        return "redirect:/products/subscribed/view/" + productId;
    }

    @GetMapping("/view/{productId}/{id}")
    public String details(final @PathVariable Integer productId,
                          final @PathVariable Long id,
                          final RedirectAttributes redirectAttributes,
                          final Model model) {
        super.loadCategories(model);
        if (!this.ticketService.exists(id) || !this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + currentProductId;
        }
        User user = this.userService.getCurrentLoggedUser();
        Product product = this.productService.findById(productId);
        Ticket ticket = this.ticketService.findById(id);
        if (!ticket.isSeen() && user == product.getProducer()) {
            ticket.setSeen(true);
            this.ticketService.edit(ticket);
        }

        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        model.addAttribute(Const.TICKET_VIEW_KEY, ticket);
        return "product/ticket/details";
    }

    @GetMapping("/delete/{ticketId}")
    public String delete(final HttpServletRequest request,
                         final @PathVariable Long ticketId,
                         final Model model,
                         final RedirectAttributes redirectAttributes) {
        super.loadCategories(model);
        if (!this.ticketService.exists(ticketId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + currentProductId;
        }

        Ticket deleteCandidate = this.ticketService.findById(ticketId);

        User user = this.userService.getCurrentLoggedUser();
        Set<Product> supportedProducts = user.getSupportedProducts();
        Product product = this.productService.findById(deleteCandidate.getProduct().getId());
        if (!supportedProducts.contains(product)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/list";
        }
        List<Ticket> ticketList = product.getTickets().stream()
                .filter(t -> t.getAuthor() == user)
                .collect(Collectors.toList());
        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        model.addAttribute(Const.TICKETS_VIEW_KEY, ticketList);
        model.addAttribute("deletionInProgress", "delete ticket");
        String redirect = request.getContextPath() + "/products/subscribed/view/" + product.getId();
        return "redirect:" + redirect;
    }

    @PostMapping("/delete/{ticketId}")
    public String deleteTicketProcess(final @PathVariable Long ticketId,
                                      final Model model,
                                      final RedirectAttributes redirectAttributes) {
        super.loadCategories(model);
        if (!this.ticketService.exists(ticketId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + currentProductId;
        }

        Ticket deleteCandidate = this.ticketService.findById(ticketId);
        for (Comment comment : deleteCandidate.getComments()) {
            this.commentService.delete(comment.getId());
        }
        long productId = deleteCandidate.getProduct().getId();
        this.ticketService.deleteById(ticketId);

        String notificationMessage = DisplayedMessages.DELETE_TICKET_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(notificationMessage, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);

        return "redirect:/products/subscribed/view/" + productId;
    }

}
