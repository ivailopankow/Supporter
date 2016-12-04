package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.binding.TicketBindingModel;
import supporter.services.product.ProductService;
import supporter.services.ticket.TicketService;
import supporter.utils.Const;

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

    private Integer currentProductId = -1;

    @ModelAttribute(Const.BINDING_MODEL_CREATE_TICKET)
    public TicketBindingModel newTicketBindingModel() {
        return new TicketBindingModel();
    }

    @GetMapping("/create/{productId}")
    public String create(final @PathVariable Integer productId, final RedirectAttributes redirectAttributes) {
        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/list";
        }
        this.currentProductId = productId;
        return "product/ticket/create";
    }

    @PostMapping("/create/{productId}")
    public String createProcess(@Valid @ModelAttribute(Const.BINDING_MODEL_CREATE_TICKET) final TicketBindingModel ticketBindingModel,
                                final BindingResult bindingResult,
                                final RedirectAttributes redirectAttributes,
                                @PathVariable Integer productId) {

        if (!this.productService.exists(productId)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/list";
        }

        if (!this.currentProductId.equals(productId) || this.currentProductId.equals(-1)) {
            super.showNonExistingResourceError(redirectAttributes);
            return "redirect:/products/subscribed/tickets/create/" + currentProductId;
        }


        return "redirect/:products/subscribed/view/" + productId;
    }

}
