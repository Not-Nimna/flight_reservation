package com.ensf480.backend.service;

import ca.ucalgary.ensf480.flightapp.model.Destination;
import ca.ucalgary.ensf480.flightapp.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public List<Destination> searchDestinations(String query) {
        return null; // Replace with actual search logic
    }

    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Optional<Destination> updateDestination(Long id, Destination destinationDetails) {
        return destinationRepository.findById(id)
            .map(destination -> {
                // Map the updated details to the existing destination entity
                destination.setCountry(destinationDetails.getCountry());
                destination.setCity(destinationDetails.getCity());
                destination.setAirportName(destinationDetails.getAirportName());
                destination.setAirportCode(destinationDetails.getAirportCode());
                return destinationRepository.save(destination);
            });
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }
}
