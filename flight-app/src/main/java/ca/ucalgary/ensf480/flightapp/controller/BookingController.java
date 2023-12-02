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

import ca.ucalgary.ensf480.flightapp.DTO.CustomerDTO;
import ca.ucalgary.ensf480.flightapp.DTO.PaymentDTO;
import ca.ucalgary.ensf480.flightapp.exception.PaymentFailedException;
import ca.ucalgary.ensf480.flightapp.exception.ResourceNotFoundException;
import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.service.BookingService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;

import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingService bookingService;
    private final AuthenticationService authenticationService;

    public BookingController(BookingService bookingService, AuthenticationService authenticationService) {
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
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

    // Add these:

    // Get bookings for a given user
    // @RequestMapping("/bookings") // get request

    // Get the bookings for a flight - used by agent
    @RequestMapping("/flight/{flightID}/bookings")
    public List<Booking> getBookingsForFlight(@PathVariable Long flightID) {
        return bookingService.getBookingsByFlight(flightID);

    }

    // Endpoint to create a new booking

    @PostMapping("flight/{flightId}/bookings/{seatId}")
    public ResponseEntity<Booking> makeBooking(@PathVariable Long flightId, @PathVariable Long seatId,
            @RequestBody BookingRequest bookingRequest) {

        try {
            // Long userId = authenticationService.getCurrentUser() != null
            // ? authenticationService.getCurrentUser().getId()
            // : null;
            // make a random userid
            Long userId = ThreadLocalRandom.current().nextLong();
            Booking booking = bookingService.makeBooking(flightId, seatId, userId, bookingRequest.getCustomerDetails(),
                    bookingRequest.getPaymentDetails());
            return ResponseEntity.ok(booking);

            } catch (PaymentFailedException ex) {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(null);
             } catch (ResourceNotFoundException ex) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
             }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint to cancel a booking
    @DeleteMapping("/{cancellationCode}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String cancellationCode) {
        boolean success = bookingService.cancelBooking(cancellationCode);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // Inner class for booking request data

    // api endpoint to get all bookings for a given user
    @GetMapping("/bookings/{userId}")
    public ResponseEntity<List<Booking>> getBookings(@PathVariable long userId) {
        try {
            // Long userId = authenticationService.getCurrentUser() != null
            // ? authenticationService.getCurrentUser().getId()
            // : null;

            List<Booking> bookings = bookingService.getBookings(userId);
            return ResponseEntity.ok(bookings);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // api endpoint to get all bookings for a given flight
    @GetMapping("/flight/{flightId}/bookings")
    public ResponseEntity<List<Booking>> getBookingsByFlight(@PathVariable long flightId) {
        try {
            // Long userId = authenticationService.getCurrentUser() != null
            // ? authenticationService.getCurrentUser().getId()
            // : null;

            List<Booking> bookings = bookingService.getBookingsByFlight(flightId);
            return ResponseEntity.ok(bookings);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
