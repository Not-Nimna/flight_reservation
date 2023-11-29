package ca.ucalgary.ensf480.flightapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.repository.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getSeatsByAircraftId(Long aircraftId) {
        // Implement logic to fetch seats for a specific aircraft
        return seatRepository.findByAircraftId(aircraftId);
    }

    public Optional<Seat> getSeatById(Long seatId) {
        // Implement logic to fetch a seat by ID
        return seatRepository.findById(seatId);
    }

    public Optional<Seat> updateSeat(Long seatId, Seat seat) {
        // Implement logic to update an existing seat
        Optional<Seat> existingSeat = seatRepository.findById(seatId);
        if (existingSeat.isPresent()) {
            Seat updatedSeat = existingSeat.get();
            // Update seat properties based on the new seat object
            updatedSeat.setSeatNumber(seat.getSeatNumber());
            updatedSeat.setSeatRow(seat.getSeatRow());
            updatedSeat.setSeatColumn(seat.getSeatColumn());
            updatedSeat.setSeatClass(seat.getSeatClass());
            updatedSeat.setAircraft(seat.getAircraft());

            return Optional.of(seatRepository.save(updatedSeat));
        } else {
            return Optional.empty();
        }
    }

    public Seat createSeat(Seat seat) {
        // Implement logic to create a new seat
        return seatRepository.save(seat);
    }

    public void deleteSeat(Long seatId) {
        // Implement logic to delete a seat
        seatRepository.deleteById(seatId);
    }
}
