package App.service;

import App.entity.Travel;
import App.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Travel> findAll() {
        return this.travelRepository.findAll();
    }

    public Optional<Travel> findById(UUID id) {
        return this.travelRepository.findById(id);
    }
}
