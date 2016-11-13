package supporter.services.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import supporter.models.Ticket;
import supporter.repositories.TicketRepository;

import java.util.List;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Service
@Primary
public class TicketServiceJpaImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Ticket> findAll() {
        return this.ticketRepository.findAll();
    }

    @Override
    public List<Ticket> findLatestFive() {
        return this.ticketRepository.findLatestFivePosts(new PageRequest(0 , 5));
    }

    @Override
    public Ticket findById(Long id) {
        return this.ticketRepository.getOne(id);
    }

    @Override
    public Ticket create(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @Override
    public Ticket edit(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(Long id) {
        this.ticketRepository.delete(id);
    }
}
