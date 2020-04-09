package App.controller;

import App.entity.Account;
import App.entity.Travel;
import App.models.CreateTravelDTO;
import App.models.User;
import App.service.AccountService;
import App.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity createTravel(@AuthenticationPrincipal User user, CreateTravelDTO dto) {
        Optional<Account> travelOwner = this.accountService.findById(UUID.fromString(dto.getOwnerId()));
        Account account = this.accountService.findOrCreate(new Account(user.getId(), user.getUsername()));

        if(travelOwner.isPresent() && account != null) {
            Travel travel = this.travelService.findOrCreate(new Travel(travelOwner.get(), dto.getPrice(), dto.getMaxPersons(), dto.getPickupDate(), dto.getPickupPoint(), dto.getEventName(), dto.getEventAddress()));

            if(travel != null) {
                return ResponseEntity.ok(travel);
            }

            return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Error occured", HttpStatus.BAD_REQUEST);
    }
}
