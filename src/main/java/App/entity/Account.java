package App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @Type(type = "uuid-char")
    @Setter
    private UUID id;

    @NotBlank
    private String fullName;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonBackReference
    private Set<AccountTravel> travels = new HashSet<>();

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonBackReference
    private Set<AccountRating> ratings = new HashSet<>();

    public Account() {}

    public Account(UUID id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

}
