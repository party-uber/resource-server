package App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "travel")
public class Travel implements Serializable {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private Account owner;

    @NotBlank
    private Double price;

    @NotBlank
    @Column(name = "pickupDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupDate;

    @NotBlank
    private String pickupPoint;

    @NotBlank
    private String eventName;

    @NotBlank
    private String eventAddress;

    @NotBlank
    private int maxPersons;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "travel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<AccountTravel> travels = new HashSet<>();

    public Travel () {}

    public Travel(Account owner, Double price, int maxPersons, Date pickupDate, String pickupPoint, String eventName, String eventAddress) {
        this.owner = owner;
        this.price = price;
        this.pickupDate = pickupDate;
        this.pickupPoint = pickupPoint;
        this.eventName = eventName;
        this.eventAddress = eventAddress;
        this.maxPersons = maxPersons;
    }

    public boolean addPerson(Account account) {
        if(this.travels.size() < maxPersons) {
            this.travels.add(new AccountTravel(this, account));
            return true;
        }

        return false;
    }
}
