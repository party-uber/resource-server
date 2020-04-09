package App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_rating")
@Getter @Setter
public class AccountRating implements Serializable {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    @NotBlank
    private Double ratingNumber;

    @NotBlank
    private Account placedBy;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonBackReference
    private Rating rating;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @JsonUnwrapped
    private Account account;

    public AccountRating() {}

    public AccountRating(Rating rating, Account account, Double ratingNumber, Account placedBy) {
        this.rating = rating;
        this.account = account;
        this.ratingNumber = ratingNumber;
        this.placedBy = placedBy;
    }

}
