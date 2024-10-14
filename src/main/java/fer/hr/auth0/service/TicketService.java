package fer.hr.auth0.service;

import fer.hr.auth0.model.Ticket;
import fer.hr.auth0.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
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

    public Long getTotalTicketsCount() {
        return ticketRepository.count();
    }

    public Optional<Ticket> getTicketById(UUID id) {
        return ticketRepository.findById(id);
    }

}
