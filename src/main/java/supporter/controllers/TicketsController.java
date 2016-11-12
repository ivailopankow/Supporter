package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import supporter.models.Ticket;
import supporter.services.NotificationService;
import supporter.services.ticket.TicketService;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Controller
public class TicketsController {

    private static final String TICKET = "ticket";
    private static final String TICKET_VIEW = "tickets/view";
    private final static String LATEST_FIVE_TICKETS = "latestFiveTickets";

    @Autowired
    private TicketService ticketService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/tickets/view/{id}")
    public String viewTicket(@PathVariable("id") Long ticketId, Model model){
        Ticket ticket = ticketService.findById(ticketId);
        List<Ticket> asideTickets = ticketService.findLatestFive();

        if (ticket == null) {
            notificationService.addErrorMessage("Cannot find ticket #" + ticketId);
            return "redirect:/";
        }
        model.addAttribute(TICKET, ticket);
        model.addAttribute(LATEST_FIVE_TICKETS, asideTickets);
        return TICKET_VIEW;
    }
}
