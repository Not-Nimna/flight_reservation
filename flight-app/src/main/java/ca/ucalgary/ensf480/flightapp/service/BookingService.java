/**
 * Service class for handling flight bookings in the flight booking application.
 * 
 * This service manages the creation and cancellation of flight bookings. It handles the 
 * logic to check flight and seat availability, link bookings to users (if logged in), 
 * and generate unique cancellation codes. It interacts with FlightRepository, 
 * SeatRepository, and BookingRepository for persistence operations.
 * 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 24, 2023
 */

package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.repository.BookingRepository;
import ca.ucalgary.ensf480.flightapp.repository.FlightRepository;
import ca.ucalgary.ensf480.flightapp.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final UserService userService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository, SeatRepository seatRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.userService = userService;
    }

    public Booking createBooking(Long flightId, Long seatId, Long userId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        Optional<Seat> seat = seatRepository.findById(seatId);

        if (flight.isPresent() && seat.isPresent() && !seat.get().isBooked()) {
            Booking booking = new Booking();
            booking.setFlight(flight.get());
            booking.setSeat(seat.get());
            // Set the user if userId is not null, assuming you have a method to fetch the user by ID
            booking.setUser(userService.findById(userId).orElse(null));
            booking.setCancellationCode(generateCancellationCode());
            seat.get().setBooked(true);
            seatRepository.save(seat.get()); // Update the seat as booked

            return bookingRepository.save(booking);
        }
        return null; // Return null if the flight or seat is not available, or if the seat is already booked
    }

    public boolean cancelBooking(String cancellationCode) {
        Optional<Booking> booking = bookingRepository.findByCancellationCode(cancellationCode);
        if (booking.isPresent()) {
            // Set the seat as not booked
            Seat seat = booking.get().getSeat();
            seat.setBooked(false);
            seatRepository.save(seat);
            
            bookingRepository.delete(booking.get());
            return true;
        }
        return false;
    }

    private String generateCancellationCode() {
        // Generate a unique cancellation code
        // For simplicity, using current timestamp. could probably be better
        return String.valueOf(System.currentTimeMillis());
    }

}
