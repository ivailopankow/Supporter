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
import supporter.models.binding.CommentBindingModel;
import supporter.services.comment.CommentService;
import supporter.services.product.ProductService;
import supporter.services.ticket.TicketService;
import supporter.services.user.UserService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import javax.validation.Valid;

/**
 * Created by Ivaylo on 11-Dec-16.
 */
@Controller
@RequestMapping("products/subscribed/tickets/view/comment/")
@PreAuthorize("isAuthenticated()")
public class CommentsController extends BaseController{

    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProductService productService;

    @ModelAttribute(Const.BINDING_MODEL_CREATE_COMMENT)
    public CommentBindingModel newTicketBindingModel() {
        return new CommentBindingModel();
    }

    @GetMapping("/{productId}/{ticketId}")
    public String addNewComment(@ModelAttribute(Const.BINDING_MODEL_CREATE_COMMENT) final CommentBindingModel commentBindingModel,
                                final @PathVariable Integer productId,
                                final @PathVariable Long ticketId,
                                final Model model, final RedirectAttributes redirectAttributes) {
        super.loadCategories(model);
        if (!this.ticketService.exists(ticketId) || !this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + ticketId;
        }

        Ticket ticket = this.ticketService.findById(ticketId);
        Product product = this.productService.findById(productId);
        model.addAttribute(Const.PRODUCT_VIEW_KEY, product);
        model.addAttribute(Const.TICKET_VIEW_KEY, ticket);
        model.addAttribute(Const.BINDING_MODEL_CREATE_COMMENT, commentBindingModel);
        model.addAttribute(Const.NEW_COMMENT_VIEW_KEY, "new ticket");
        return "product/ticket/details";
    }

    @PostMapping("/create/{ticketId}")
    public String addNewCommentProcess(@Valid @ModelAttribute(Const.BINDING_MODEL_CREATE_COMMENT) final CommentBindingModel commentBindingModel,
                                       final @PathVariable Long ticketId, final Model model, final BindingResult bindingResult,
                                       final RedirectAttributes redirectAttributes) {
        super.loadCategories(model);
        if (!this.ticketService.exists(ticketId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + ticketId;
        }

        if (bindingResult.hasErrors()) {
            String messageText = DisplayedMessages.ERROR_IN_FORM;
            NotificationMessage message = super.generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_RESULT_COMMENT, bindingResult);
            redirectAttributes.addFlashAttribute(Const.BINDING_MODEL_CREATE_COMMENT, commentBindingModel);
            redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
            return "redirect:/products/subscribed/tickets/create/" + ticketId;
        }

        User user = this.userService.getCurrentLoggedUser();
        Ticket ticket = this.ticketService.findById(ticketId);
        Comment comment = new Comment(commentBindingModel.getContent(), user, ticket);
        ticket.getComments().add(comment);
        user.getComments().add(comment);
        this.commentService.create(comment);

        String notificationMessage = DisplayedMessages.CREATE_COMMENT_SUCCESS;
        NotificationMessage message = super.generateNotificationMessage(notificationMessage, NotificationMessage.Type.INFO);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);


        model.addAttribute(Const.TICKET_VIEW_KEY, ticket);
        return "redirect:/products/subscribed/tickets/view/" + ticketId;
    }
}
