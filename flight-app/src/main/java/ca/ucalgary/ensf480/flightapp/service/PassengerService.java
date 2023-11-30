package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.Passenger;
import ca.ucalgary.ensf480.flightapp.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Move the logic of this service to the booking service.


@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepository.findById(id);
        bookingRepository.findByFlight(id);
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public List<Passenger> searchPassengers(String query) {
        return null; // Replace with actual search logic
    }   

    public Passenger createPassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
