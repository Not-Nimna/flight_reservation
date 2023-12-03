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

import ca.ucalgary.ensf480.flightapp.DTO.BookingDTO;
import ca.ucalgary.ensf480.flightapp.DTO.CustomerDTO;
import ca.ucalgary.ensf480.flightapp.DTO.PaymentDTO;
import ca.ucalgary.ensf480.flightapp.exception.PaymentFailedException;
import ca.ucalgary.ensf480.flightapp.exception.ResourceNotFoundException;
import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.service.BookingService;
import ca.ucalgary.ensf480.flightapp.service.FlightService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    FlightService flightService;

    @Autowired
    AuthenticationService authenticationService;


    // Endpoint to create a new booking
    @PostMapping("/public/flights/{flightId}/bookings/{seatId}")
    public ResponseEntity<Booking> makeBooking(@PathVariable Long flightId, @PathVariable Long seatId,
            @RequestBody BookingRequest bookingRequest) {

        try {
            User user = authenticationService.getCurrentUser();
            Booking booking = bookingService.makeBooking(flightId, seatId, user, bookingRequest.getCustomerDetails(),
                    bookingRequest.getPaymentDetails());
            if (booking != null) {
                return ResponseEntity.ok(booking);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

        } catch (PaymentFailedException ex) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(null);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint to cancel a booking
    @DeleteMapping("/public/bookings/{cancellationCode}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String cancellationCode) {
        boolean success = bookingService.cancelBooking(cancellationCode);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // api endpoint to get all bookings for a given user
    @GetMapping("/user/bookings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingDTO>> getBookings() {
        try {
            User user = authenticationService.getCurrentUser();
            List<Booking> bookings = bookingService.getBookingsByUser(user);
            List<BookingDTO> bookingDTOS = bookings.stream()
                                                .map(booking -> new BookingDTO(booking)) // Assuming BookingDTO has a constructor that takes a Booking object
                                                .collect(Collectors.toList()); // Corrected line
            return ResponseEntity.ok(bookingDTOS); // Return the DTO list, not the entity list
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            ex.printStackTrace(); // For debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // AGENT stuff -- Passengers

    // Get the bookings for a flight - restricted to agents
    @GetMapping("/flights/{flightId}/bookings")
    @PreAuthorize("hasRole('AGENT')")
    public Set<Booking> getBookingsForFlight(@PathVariable Long flightId) {
        return flightService.getBookings(flightId);
    }

    // Get passenger by ID - restricted to agents
    @GetMapping("/flight/{flightId}/bookings/{id}")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Booking> getPassengerById(@PathVariable Long flightId, @PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return ResponseEntity.ok(booking);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a passenger - restricted to agents
    @DeleteMapping("/flight/{flightId}/bookings/{id}")
    @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Booking> deletePassenger(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }


    public static class BookingRequest {
        private CustomerDTO customerDetails;
        private PaymentDTO paymentDetails;

        // Standard getters and setters

        public CustomerDTO getCustomerDetails() {
            return this.customerDetails;
        }

        public void setCustomerDetails(CustomerDTO customerDetails) {
            this.customerDetails = customerDetails;
        }

        public PaymentDTO getPaymentDetails() {
            return this.paymentDetails;
        }

        public void setPaymentDetails(PaymentDTO paymentDetails) {
            this.paymentDetails = paymentDetails;
        }
    }

}
