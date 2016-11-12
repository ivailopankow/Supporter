package supporter.services.ticket;

import java.util.List;

import supporter.models.Ticket;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public interface TicketService {
    List<Ticket> findAll();
    List<Ticket> findLatestFive();
    Ticket findById(Long id);
    Ticket create(Ticket ticket);
    Ticket edit(Ticket ticket);
    void deleteById(Long id);
}
