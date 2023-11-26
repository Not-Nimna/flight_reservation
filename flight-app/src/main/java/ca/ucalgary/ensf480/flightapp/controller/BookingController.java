/**
 * REST controller for flight bookings in the flight booking application.
 *
 * Handles REST endpoints for booking creation and cancellation, utilizing 
 * BookingService for business logic and AuthenticationService for user context. 
 * Includes an inner class, BookingRequest, for data encapsulation.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 26, 2023
 */

package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.service.BookingService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final AuthenticationService authenticationService;

    @Autowired
    public BookingController(BookingService bookingService, AuthenticationService authenticationService) {
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
    }

    // Endpoint to create a new booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest bookingRequest) {
        Long userId = authenticationService.getCurrentUser() != null ? authenticationService.getCurrentUser().getId() : null;
        Booking booking = bookingService.createBooking(bookingRequest.getFlightId(), bookingRequest.getSeatId(), userId);
        return booking != null ? ResponseEntity.ok(booking) : ResponseEntity.badRequest().build();
    }

    // Endpoint to cancel a booking
    @DeleteMapping
    public ResponseEntity<Void> cancelBooking(@RequestParam String cancellationCode) {
        boolean cancelled = bookingService.cancelBooking(cancellationCode);
        return cancelled ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Inner class for booking request
    private static class BookingRequest {
        private Long flightId;
        private Long seatId;

        // Getters and setters
        public Long getFlightId() { return flightId; }
        public void setFlightId(Long flightId) { this.flightId = flightId; }
        public Long getSeatId() { return seatId; }
        public void setSeatId(Long seatId) { this.seatId = seatId; }
    }
}
