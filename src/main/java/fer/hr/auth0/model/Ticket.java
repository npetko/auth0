package fer.hr.auth0.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
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

    public Ticket(String vatin, String firstName, String lastName) {
        this.vatin = vatin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
    }

}
