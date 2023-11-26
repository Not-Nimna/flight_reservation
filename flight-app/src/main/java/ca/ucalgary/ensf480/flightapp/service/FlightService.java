package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> searchFlights(String query) {
        return null; // Replace with actual search logic
    }

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Optional<Flight> updateFlight(Long id, Flight flightDetails) {
        return flightRepository.findById(id)
            .map(flight -> {
                // Map the updated details to the existing flight entity
                flight.setFlightNumber(flightDetails.getFlightNumber());
                // Set other fields from flightDetails to flight as needed
                return flightRepository.save(flight);
            });
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}
