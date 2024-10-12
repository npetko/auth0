package fer.hr.auth0.controller;

import fer.hr.auth0.dto.TicketRequest;
import fer.hr.auth0.model.Ticket;
import fer.hr.auth0.service.TicketService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private TicketService ticketService;

    public ResponseEntity<?> generateTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketService.createTicket(
                    ticketRequest.getVatin(),
                    ticketRequest.getFirstName(),
                    ticketRequest.getLastName()
            );
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
