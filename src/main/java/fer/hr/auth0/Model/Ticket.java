package fer.hr.auth0.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private UUID id;

    @Column
    private String vatin;

    @Column
    private String firstName;

    @Column
    private  String lastName;

    @Column
    private LocalDateTime createdAt;

}
