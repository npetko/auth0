package fer.hr.auth0.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketRequest {

    private String vatin;
    private String firstName;
    private String lastName;

}
