package App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter
@Table(name = "rating")
public class Rating implements Serializable {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String name;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "rating",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<AccountRating> ratings = new HashSet<>();

    public Rating() {}

    public Rating(Account account, Double rating, Account placedBy) {
        this.ratings.add(new AccountRating(this, account, rating, placedBy));
    }

    public void addRating(Account account, Double rating, Account placedBy) {
        this.ratings.add(new AccountRating(this, account, rating, placedBy));
    }

}
