package supporter.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supporter.models.Ticket;

import java.util.List;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t LEFT JOIN FETCH t.author ORDER BY t.date DESC")
    List<Ticket> findLatestFivePosts(Pageable pageable);

}
