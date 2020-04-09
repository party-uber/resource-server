package App.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CreateTravelDTO {
    @NotEmpty(message = "Please provide: Owner id")
    private String ownerId;

    @NotEmpty(message = "Please provide: Price")
    private Double price;

    @NotEmpty(message = "Please provide: Max persons")
    private int maxPersons;

    @NotEmpty(message = "Please provide: Pickup Date")
    private Date pickupDate;

    @NotEmpty(message = "Please provide: Pickup Point")
    private String pickupPoint;

    @NotEmpty(message = "Please provide: Event Name")
    private String eventName;

    @NotEmpty(message = "Please provide: Event Address")
    private String eventAddress;
}
