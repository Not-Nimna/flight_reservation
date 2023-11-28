package com.ensf480.backend.service;

import ca.ucalgary.ensf480.flightapp.model.Aircraft;
import ca.ucalgary.ensf480.flightapp.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    public Optional<Aircraft> getAircraftById(Long id) {
        return aircraftRepository.findById(id);
    }

    public List<Aircraft> getAllAircraft() {
        return aircraftRepository.findAll();
    }

    public List<Aircraft> searchAircraft(String query) {
        return null; // Replace with actual search logic
    }

    public Aircraft createAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    // Needs to be changed
    public Optional<Aircraft> updateAircraft(Long id, Aircraft aircraftDetails) {
        return aircraftRepository.findById(id)
            .map(aircraft -> {
                // Map the updated details to the existing aircraft entity
                aircraft.setCode(aircraftDetails.getCode());
                aircraft.setModel(aircraftDetails.getModel());
                // aircraft.setTotalSeats(aircraftDetails.getTotalSeats());
                return aircraftRepository.save(aircraft);
            });
    }

    public void deleteAircraft(Long id) {
        aircraftRepository.deleteById(id);
    }    
}
