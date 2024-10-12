package fer.hr.auth0.Repository;

import fer.hr.auth0.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByVatin(String vatin);

}
