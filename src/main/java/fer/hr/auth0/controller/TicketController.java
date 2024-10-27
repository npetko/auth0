package fer.hr.auth0.controller;

import fer.hr.auth0.dto.TicketRequest;
import fer.hr.auth0.model.Ticket;
import fer.hr.auth0.service.QRCodeService;
import fer.hr.auth0.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/generate")
    @CrossOrigin(value = "*", allowedHeaders = "*")
    public ResponseEntity<?> generateTicket(@RequestBody TicketRequest ticketRequest) {
        if (ticketRequest.getVatin() == null || ticketRequest.getVatin().isEmpty() ||
                ticketRequest.getFirstName() == null || ticketRequest.getFirstName().isEmpty() ||
                ticketRequest.getLastName() == null || ticketRequest.getLastName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nisu uneseni svi potrebni podatci.");
        }

        if (!ticketRequest.getVatin().matches("\\d{11}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OIB mora biti točno 11 znamenki.");
        }

        try {
            Ticket ticket = ticketService.createTicket(
                    ticketRequest.getVatin(),
                    ticketRequest.getFirstName(),
                    ticketRequest.getLastName()
            );

            String ticketUrl = "https://auth0-6y8l.onrender.com/" + ticket.getId();
            byte[] qrCodeImage = qrCodeService.generateQRCodeImage(ticketUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().contains("Generiran maksimalan broj karata")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating ticket or QR code");
        }
    }

    @GetMapping("/count")
    @CrossOrigin(value = "*", allowedHeaders = "*")
    public ResponseEntity<Long> getTotalTickets() {
        Long count = ticketService.getTotalTicketsCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}")
    @CrossOrigin(value = "*", allowedHeaders = "*")
    public ResponseEntity<Ticket> getTicketDetails(@PathVariable UUID id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);

        return ticket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/ticket/{ticketId}")
    @CrossOrigin(value = "*", allowedHeaders = "*")
    public ResponseEntity<Void> serveTicketPage(@PathVariable UUID ticketId) {
        String redirectUrl = "/ticket.html?ticketId=" + ticketId;
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

}
