package supporter.services.ticket;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import supporter.models.Ticket;
import supporter.models.User;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Service
public class TicketServiceStubImpl
        implements TicketService {

    //region Fields
    private List<Ticket> tickets;
//endregion Fields

    //region Constructors
    private TicketServiceStubImpl() {
        init();
    }
//endregion Constructors

    //region Overridden Methods
    @Override
    public List<Ticket> findAll() {
        return this.tickets;
    }

    @Override
    public List<Ticket> findLatestFive() {
        return this.tickets.stream()
                           .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                           .limit(5)
                           .collect(Collectors.toList());
    }

    @Override
    public Ticket findById(Long id) {
        return this.tickets.stream()
                           .filter(ticket -> Objects.equals(ticket.getId(), id))
                           .findFirst()
                           .orElse(null);
    }

    @Override
    public Ticket create(Ticket ticket) {
        ticket.setId(this.tickets.stream().mapToLong(
                Ticket::getId).max().getAsLong() + 1);
        this.tickets.add(ticket);
        return ticket;
    }

    @Override
    public Ticket edit(Ticket ticket) {
        for (int i = 0; i < this.tickets.size(); i++) {
            if (Objects.equals(this.tickets.get(i).getId(), ticket.getId())) {
                this.tickets.set(i, ticket);
                return ticket;
            }
        }
        throw new RuntimeException("Ticket not found: " + ticket.getId());

    }

    @Override
    public void deleteById(Long id) {
        for (int i = 0; i < this.tickets.size(); i++) {
            if (Objects.equals(this.tickets.get(i).getId(), id)) {
                this.tickets.remove(i);
                return;
            }
        }
        throw new RuntimeException("Ticket not found: " + id);
    }
//endregion Overridden Methods

    //region Helper Methods
    private void init() {
        this.tickets = Arrays.asList(
                new Ticket(1L, "First Ticket", "<p>Line #1.</p><p>Line #2</p>", null),
                new Ticket(2L, "Second Ticket",
                           "Second Ticket content:<ul><li>line 1</li><li>line 2</li></p>",
                           new User(10L, User.Category.CONSUMER, "pesho10", "Peter Ivanov")),
                new Ticket(3L, "Ticket #3", "<p>The ticket number 3 nice</p>",
                           new User(10L, User.Category.CONSUMER, "merry", null)),
                new Ticket(4L, "Forth Ticket", "<p>Not interesting ticket</p>", null),
                new Ticket(5L, "Ticket Number 5", "<p>Just posting</p>", null),
                new Ticket(6L, "Sixth Ticket", "<p>Another interesting ticket</p>", null)
        );
    }
//endregion Helper Methods
}
