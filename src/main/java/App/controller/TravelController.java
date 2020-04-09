package App.controller;

import App.entity.Account;
import App.entity.Travel;
import App.models.CreateTravelDTO;
import App.models.User;
import App.service.AccountService;
import App.service.TravelService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/travel")
public class TravelController {
    private final AccountService accountService;
    private final TravelService travelService;

    @Autowired
    public TravelController(AccountService accountService, TravelService travelService) {
        this.accountService = accountService;
        this.travelService = travelService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllTravels() {
        List<Travel> travels = this.travelService.findAll();

        if(!travels.isEmpty()) {
            return ResponseEntity.ok(travels);
        }

        return new ResponseEntity<>("No travels found", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createTravel(@AuthenticationPrincipal User user, @Valid @RequestBody CreateTravelDTO dto) {
        Account account = this.accountService.findOrCreate(new Account(user.getId(), user.getUsername()));

        if(account != null) {
            Travel travel = this.travelService.findOrCreate(new Travel(account, dto.getPrice(), dto.getMaxPersons(), dto.getPickupDate(), dto.getPickupPoint(), dto.getEventName(), dto.getEventAddress()));

            if(travel != null) {
                return ResponseEntity.ok(travel);
            }

            return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "apply")
    public ResponseEntity applyTravel(@AuthenticationPrincipal User user, @Valid @RequestBody CreateTravelDTO dto) {
        Account account = this.accountService.findOrCreate(new Account(user.getId(), user.getUsername()));

        if(account != null) {

        }

        return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
    }

}
