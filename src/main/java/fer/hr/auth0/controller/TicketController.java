package fer.hr.auth0.controller;

import fer.hr.auth0.dto.TicketRequest;
import fer.hr.auth0.model.Ticket;
import fer.hr.auth0.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateTicket(@RequestBody TicketRequest ticketRequest) {
        try {
            Ticket ticket = ticketService.createTicket(
                    ticketRequest.getVatin(),
                    ticketRequest.getFirstName(),
                    ticketRequest.getLastName()
            );
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/count")
    @CrossOrigin(value = "*", allowedHeaders = "*")
    public ResponseEntity<Long> getTotalTickets() {
        Long count = ticketService.getTotalTicketsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketDetails(@PathVariable UUID id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.isPresent() ? ResponseEntity.ok(ticket.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
    }

}
