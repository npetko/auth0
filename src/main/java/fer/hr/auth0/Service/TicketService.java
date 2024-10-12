package fer.hr.auth0.Service;

import fer.hr.auth0.Model.Ticket;
import fer.hr.auth0.Repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    public List<Ticket> getTicketsByVatin(String vatin) {
        return ticketRepository.findByVatin(vatin);
    }

    public Ticket createTicket(String vatin, String firstName, String lastName) throws Exception {
        if (getTicketsByVatin(vatin).size() >= 3) {
            throw new Exception("Maximum number of tickets reached for OIB: " + vatin);
        }
        return ticketRepository.save(new Ticket(vatin, firstName, lastName));
    }

}
