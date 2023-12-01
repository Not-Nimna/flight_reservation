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

import ca.ucalgary.ensf480.flightapp.DTO.CustomerDTO;
import ca.ucalgary.ensf480.flightapp.DTO.PaymentDTO;
import ca.ucalgary.ensf480.flightapp.DTO.SeatBookingDTO;
import ca.ucalgary.ensf480.flightapp.exception.PaymentFailedException;
import ca.ucalgary.ensf480.flightapp.exception.ResourceNotFoundException;
import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Customer;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.Passenger;
import ca.ucalgary.ensf480.flightapp.model.Payment;
import ca.ucalgary.ensf480.flightapp.model.PaymentStatus;
import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.model.User;
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

    public Booking makeBooking(Long flightId, Long seatId, Long userId, CustomerDTO customerDTO, PaymentDTO paymentDTO) {
        Optional<Seat> optionalSeat = seatRepository.findById(seatId);
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalSeat.isEmpty() || optionalFlight.isEmpty()) {
            throw new ResourceNotFoundException("Seat or Flight not found");
        }

        Seat seat = optionalSeat.get();
        Flight flight = optionalFlight.get();
        User user = userId != null ? userService.findById(userId).orElse(null) : null;

        // Check if there is already a booking for this seat on this flight
        if (bookingRepository.findBySeatAndFlight(seat, flight).isPresent()) {
            return null; // Indicates the seat is already booked
        }

        BigDecimal price = pricingService.calculatePrice(seat, flight);
        price = pricingService.calculatePromoPrice(flight, user, price);

        Customer customer = customerService.createOrUpdateCustomer(customerDTO, user);

        // Create payment
        Payment payment = paymentService.createPayment(paymentDTO, price, customer);

        // Only create booking if payment is successful


        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new PaymentFailedException("Payment processing failed");
        }

        
        Booking booking = new Booking(flight, seat, price, user, payment);

        Passenger passenger = new Passenger(customer.getName());
        flight.addPassenger(passenger);

        return bookingRepository.save(booking); // Save the successful booking


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
                BigDecimal currentPrice = pricingService.calculatePrice(seat, flight);
                currentPrice = pricingService.calculatePromoPrice(flight, user, currentPrice);

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
