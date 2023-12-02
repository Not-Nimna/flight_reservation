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
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.service.BookingService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    AuthenticationService authenticationService;

    // Get the bookings for a flight - used by agent
    @GetMapping("/flights/{flightID}/bookings")
    @PreAuthorize("hasRole('AGENT')")
    public List<Booking> getBookingsForFlight(@PathVariable Long flightID) {
        return bookingService.getBookingsByFlight(flightID);
    }

    // Endpoint to create a new booking
    @PostMapping("/public/flights/{flightId}/bookings/{seatId}")
    public ResponseEntity<Booking> makeBooking(@PathVariable Long flightId, @PathVariable Long seatId,
            @RequestBody BookingRequest bookingRequest) {

        try {
            User user = authenticationService.getCurrentUser();
            Booking booking = bookingService.makeBooking(flightId, seatId, user, bookingRequest.getCustomerDetails(),
                    bookingRequest.getPaymentDetails());
            return ResponseEntity.ok(booking);

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
    public ResponseEntity<List<Booking>> getBookings(@PathVariable long userId) {
        try {
            // Fix this
            User user = authenticationService.getCurrentUser();
            List<Booking> bookings = bookingService.getBookings(userId);
            return ResponseEntity.ok(bookings);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
