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

import ca.ucalgary.ensf480.flightapp.model.SeatBookingDTO;

import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Customer;

import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.model.PaymentDetails;

import ca.ucalgary.ensf480.flightapp.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerService customerService;
    private final PaymentService paymentService;

    private final UserService userService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerService customerService, PaymentService paymentService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.customerService = customerService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    public Booking makeBooking(Long bookingId, Long userId, String customerEmail, PaymentDetails paymentDetails) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            if (!booking.isBooked()) {
                Customer customer = customerService.createOrUpdateCustomer(customerEmail, userId);
                Payment payment = paymentService.createPayment(paymentDetails, booking.getPrice());

                booking.setCustomer(customer);
                booking.setPayment(payment);
                booking.setUser(userId != null ? userService.findById(userId).orElse(null) : null);
                booking.setBooked(true);

                return bookingRepository.save(booking);
            }
        }
        return null;
    }



    public boolean cancelBooking(String cancellationCode) {
        Optional<Booking> optionalBooking = bookingRepository.findByCancellationCode(cancellationCode);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            if (booking.isBooked()) {
                booking.setBooked(false);
                bookingRepository.save(booking);

                return true;
            }
        }
        return false;
    }

    public List<SeatBookingDTO> getSeatMap(Long flightId) {
        List<Booking> bookings = bookingRepository.findByFlightId(flightId);

        return bookings.stream()
                       .map(booking -> new SeatBookingDTO(booking.getSeat(), booking.isBooked()))
                       .collect(Collectors.toList());
    }






    


}
