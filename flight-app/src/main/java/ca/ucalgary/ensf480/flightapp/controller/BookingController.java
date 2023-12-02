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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight/{flightId}/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final AuthenticationService authenticationService;

    @Autowired
    public BookingController(BookingService bookingService, AuthenticationService authenticationService) {
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
    }

    // Endpoint to create a new booking
    @PostMapping("/{seatId}")
    public ResponseEntity<Booking> makeBooking(@PathVariable Long flightId, @PathVariable Long seatId, 
                                               @RequestBody BookingRequest bookingRequest) {
        try {
            Long userId = authenticationService.getCurrentUser() != null ? authenticationService.getCurrentUser().getId() : null;
            Booking booking = bookingService.makeBooking(flightId, seatId, userId, bookingRequest.getCustomerDetails(), bookingRequest.getPaymentDetails());
            return ResponseEntity.ok(booking);
        } catch (PaymentFailedException ex) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(null);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint to cancel a booking
    @DeleteMapping("/{cancellationCode}")
    public ResponseEntity<Void> cancelBooking(@PathVariable String cancellationCode) {
        boolean success = bookingService.cancelBooking(cancellationCode);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // Inner class for booking request data
    public static class BookingRequest {
        private CustomerDTO customerDetails;
        private PaymentDTO paymentDetails;
    
        // Standard getters and setters
    
        public CustomerDTO getCustomerDetails() {
            return customerDetails;
        }
    
        public void setCustomerDetails(CustomerDTO customerDetails) {
            this.customerDetails = customerDetails;
        }
    
        public PaymentDTO getPaymentDetails() {
            return paymentDetails;
        }
    
        public void setPaymentDetails(PaymentDTO paymentDetails) {
            this.paymentDetails = paymentDetails;
        }
    }
    
    
}
