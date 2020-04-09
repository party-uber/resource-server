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
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity getAllTravels(@AuthenticationPrincipal User user) {
        this.accountService.findOrCreate(new Account(user.getId(), user.getUsername()));

        List<Travel> travels = this.travelService.findAll();

        if(!travels.isEmpty()) {
            return ResponseEntity.ok(travels);
        }

        return new ResponseEntity<>("No travels found", HttpStatus.OK);
    }

    @GetMapping(value = "/{travelId}")
    public ResponseEntity getTravel(@AuthenticationPrincipal User user, @PathVariable String travelId) {
        this.accountService.findOrCreate(new Account(user.getId(), user.getUsername()));

        Optional<Travel> travel = this.travelService.findById(UUID.fromString(travelId));

        if(travel.isPresent()) {
            return ResponseEntity.ok(travel.get());
        }

        return new ResponseEntity<>("No travel found with this id", HttpStatus.BAD_REQUEST);
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

    @PostMapping(value = "apply/{travelId}")
    public ResponseEntity applyTravel(@AuthenticationPrincipal User user, @PathVariable String travelId) {
        Account account = this.accountService.findOrCreate(new Account(user.getId(), user.getUsername()));

        if(account != null) {
            Optional<Travel> travel = this.travelService.findById(UUID.fromString(travelId));

            if(travel.isPresent()) {
                if(!travel.get().containUser(account) && !travel.get().isOwner(account)) {

                    travel.get().addPerson(account);
                    this.travelService.findOrCreate(travel.get());

                    return ResponseEntity.ok(travel.get());
                } else {
                    return new ResponseEntity<>("You already applied or is the owner", HttpStatus.BAD_REQUEST);
                }
            }

            return new ResponseEntity<>("No travel has been found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
    }
}
