package App.service;

import App.entity.Travel;
import App.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TravelService {
    private final TravelRepository travelRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public Travel findOrCreate(Travel travel) {
        return this.travelRepository.save(travel);
    }
}
