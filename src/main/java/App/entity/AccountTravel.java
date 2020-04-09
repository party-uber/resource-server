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
import java.io.Serializable;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_travel")
@Getter @Setter
public class AccountTravel implements Serializable {

    @Id
    @Type(type="uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonBackReference
    private Travel travel;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @JsonUnwrapped
    private Account account;

    public AccountTravel() {}

    public AccountTravel(Travel travel, Account account) {
        this.travel = travel;
        this.account = account;
    }

}
