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

import ca.ucalgary.ensf480.flightapp.DTO.SeatBookingDTO;
import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Customer;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.model.PaymentDetails;
import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.repository.BookingRepository;
import ca.ucalgary.ensf480.flightapp.repository.FlightRepository;
import ca.ucalgary.ensf480.flightapp.repository.SeatRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerService customerService;
    private final PaymentService paymentService;

    private final UserService userService;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final PricingService pricingService;

    @Autowired
    public BookingService(
        BookingRepository bookingRepository, 
        CustomerService customerService, 
        PaymentService paymentService, 
        UserService userService,
        FlightRepository flightRepository,
        SeatRepository seatRepository,
        PricingService pricingService) {

        this.bookingRepository = bookingRepository;
        this.customerService = customerService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.pricingService = pricingService;

    }

    public Booking makeBooking(Long flightId, Long seatId, Long userId, String customerEmail, PaymentDetails paymentDetails) {
        Optional<Seat> optionalSeat = seatRepository.findById(seatId);
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalSeat.isPresent() && optionalFlight.isPresent()) {
            Seat seat = optionalSeat.get();
            Flight flight = optionalFlight.get();

            // Check if there is already a booking for this seat on this flight
            if (bookingRepository.findBySeatAndFlight(seat, flight).isPresent()) {
                // Return null or throw an exception as per your design decision
                return null; // Indicates the seat is already booked
            }

            BigDecimal price = pricingService.calculatePrice(seat, flight);

            Customer customer = customerService.createOrUpdateCustomer(customerEmail, userId);
            Payment payment = paymentService.createPayment(paymentDetails, price);

            Booking booking = new Booking();
            booking.setSeat(seat);
            booking.setPricePaid(price); // Assuming pricePaid is the field name in Booking
            booking.setCustomer(customer);
            booking.setPayment(payment);
            booking.setUser(userId != null ? userService.findById(userId).orElse(null) : null);

            return bookingRepository.save(booking);
        }
        return null;
    }


    public boolean cancelBooking(String cancellationCode) {
        Optional<Booking> optionalBooking = bookingRepository.findByCancellationCode(cancellationCode);

        if (optionalBooking.isPresent()) {
            bookingRepository.delete(optionalBooking.get());
            return true;
        }
        return false;
    }

    
    public List<SeatBookingDTO> getSeatMap(Long flightId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            Set<Seat> seats = flight.getAircraft().getSeats(); // Getting seats from the aircraft
            List<Booking> bookings = bookingRepository.findByFlight(flight);

            return seats.stream().map(seat -> {
                // Calculate the current price for the seat
                BigDecimal currentPrice = pricingService.calculatePrice(seat, flight);

                // Check if the seat is booked
                boolean isBooked = bookings.stream()
                                           .anyMatch(booking -> booking.getSeat().getId().equals(seat.getId()));

                // Create a new DTO with the price and booking status
                return new SeatBookingDTO(seat, isBooked, currentPrice);
            }).collect(Collectors.toList());
        }
        return null; // Or handle this case as per your application's requirements
    }

}
