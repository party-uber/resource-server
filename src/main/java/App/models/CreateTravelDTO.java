package App.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class CreateTravelDTO {

    private Double price;

    private int maxPersons;

    private Date pickupDate;

    private String pickupPoint;

    private String eventName;

    private String eventAddress;
}
