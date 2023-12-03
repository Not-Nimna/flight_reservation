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

import ca.ucalgary.ensf480.flightapp.DTO.BookingDTO;
import ca.ucalgary.ensf480.flightapp.DTO.CustomerDTO;
import ca.ucalgary.ensf480.flightapp.DTO.PaymentDTO;
import ca.ucalgary.ensf480.flightapp.DTO.SeatBookingDTO;
import ca.ucalgary.ensf480.flightapp.email.services.EmailService;
import ca.ucalgary.ensf480.flightapp.exception.PaymentFailedException;
import ca.ucalgary.ensf480.flightapp.exception.ResourceNotFoundException;
import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Customer;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.model.PaymentStatus;
import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.BookingRepository;
import ca.ucalgary.ensf480.flightapp.repository.FlightRepository;
import ca.ucalgary.ensf480.flightapp.repository.SeatRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    PricingService pricingService;

    @Autowired
    EmailService emailService;

    public Booking makeBooking(Long flightId, Long seatId, User user, CustomerDTO customerDTO,
            PaymentDTO paymentDTO) {
        Optional<Seat> optionalSeat = seatRepository.findById(seatId);
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalSeat.isEmpty() || optionalFlight.isEmpty()) {
            throw new ResourceNotFoundException("Seat or Flight not found");
        }

        Seat seat = optionalSeat.get();
        Flight flight = optionalFlight.get();

        // Check if there is already a booking for this seat on this flight
        if (bookingRepository.findBySeatAndFlight(seat, flight).isPresent()) {
            return null; // Indicates the seat is already booked
        }

        BigDecimal price = pricingService.calculatePrice(seat, flight, user);

        Customer customer = customerService.createOrUpdateCustomer(customerDTO, user);

        // Create payment - this will send the recipt
        Payment payment = paymentService.createPayment(paymentDTO, price, customer);

        // Only create booking if payment is successful

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new PaymentFailedException("Payment processing failed");
        }

        Booking booking = new Booking(customer.getName(), customer.getEmail(), flight, seat, price, user, payment);

        Booking booking_saved = bookingRepository.save(booking); // Save the successful booking

        if (booking != null) {
            // Send ticket
            BookingDTO bookingDTO = new BookingDTO(booking_saved);
            emailService.sendTicketEmail(bookingDTO);

        }
        return booking_saved;

    }

    public boolean cancelBooking(String cancellationCode) {
        Optional<Booking> optionalBooking = bookingRepository.findByCancellationCode(cancellationCode);

        if (optionalBooking.isPresent()) {
            bookingRepository.delete(optionalBooking.get());
            return true;
        }
        return false;
    }

    
    public List<SeatBookingDTO> getSeatMap(Long flightId, User user) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            Set<Seat> seats = flight.getAircraft().getSeats(); // Getting seats from the aircraft
            List<Booking> bookings = bookingRepository.findByFlight(flight);

            return seats.stream().map(seat -> {
                // Calculate the current price for the seat
                BigDecimal currentPrice = pricingService.calculatePrice(seat, flight, user);

                // Check if the seat is booked
                boolean isBooked = bookings.stream()
                        .anyMatch(booking -> booking.getSeat().getId().equals(seat.getId()));

                // Create a new DTO with the price and booking status
                return new SeatBookingDTO(seat, isBooked, currentPrice);
            }).collect(Collectors.toList());
        }
        return null; // Or handle this case as per your application's requirements
    }

    public List<Booking> getBookings(long userId) {

        return bookingRepository.findByUserId(userId);

    }

    public List<Booking> getBookingsByFlight(long flightId) {

        return bookingRepository.findByFlightId(flightId);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> 
            new EntityNotFoundException("Booking not found with id: " + id));
    }


    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
    

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }
    
}
