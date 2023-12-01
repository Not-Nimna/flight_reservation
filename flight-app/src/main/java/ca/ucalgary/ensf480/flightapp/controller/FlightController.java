/**
 * REST controller for managing flights in the flight booking application.
 *
 * Provides endpoints to retrieve, search, create, update, and delete flights. 
 * Uses FlightService for flight operations and AuthenticationService for user 
 * authorization, with special permissions for admin users on certain operations.
 *
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Nov 26, 2023
 */

package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.DTO.FlightDTO;
import ca.ucalgary.ensf480.flightapp.DTO.SeatBookingDTO;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.Passenger;
import ca.ucalgary.ensf480.flightapp.service.FlightService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import ca.ucalgary.ensf480.flightapp.service.BookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import ca.ucalgary.ensf480.flightapp.model.Promo;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:3000")

public class FlightController {

    private final FlightService flightService;
    private final BookingService bookingService;
    private final AuthenticationService authenticationService;

    // @Autowired
    public FlightController(FlightService flightService, BookingService bookingService,
            AuthenticationService authenticationService) {

        this.flightService = flightService;
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
    }

    // Get all flights - accessible to all users
    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    // // GET endpoint to retrieve seat map for a flight
    // @GetMapping("/{id}/seatMap")
    // public ResponseEntity<List<SeatBookingDTO>> getSeatMap(@PathVariable Long id)
    // {
    // List<SeatBookingDTO> seatMap = bookingService.getSeatMap(id);
    // return ResponseEntity.ok(seatMap);
    // }
    @GetMapping("/{id}/seatMap")
    public ResponseEntity<List<SeatBookingDTO>> getSeatMap(@PathVariable Long id) {
        List<SeatBookingDTO> seatMap = bookingService.getSeatMap(id);

        Promo promotion = flightService.getFlightById(id).get().getPromo();
        boolean isUserRegistered = authenticationService.getCurrentUser() != null;

        if (promotion != null && isUserRegistered) {
            // Apply promotion discount to each seat
            seatMap.forEach(seatBookingDTO -> {

                double discount = promotion.getDiscount();
                BigDecimal originalPrice = seatBookingDTO.getPrice();

                BigDecimal discountedPrice = applyPromotion(originalPrice, BigDecimal.valueOf(discount));
                seatBookingDTO.setPrice(discountedPrice);
            });
        }

        return ResponseEntity.ok(seatMap);
    }

    private BigDecimal applyPromotion(BigDecimal originalPrice, BigDecimal discount) {
        // Apply the promotion discount to the original price
        BigDecimal discountedPrice = originalPrice.subtract(originalPrice.multiply(discount));
        // Ensure the discounted price is non-negative
        return discountedPrice.max(BigDecimal.ZERO);
    }

    // Get a single flight by ID - accessible to all users
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search flights - accessible to all users
    @GetMapping("/search/{destination}")
    public ResponseEntity<List<FlightDTO>> searchFlights(@PathVariable String destination) {
        List<FlightDTO> flights = flightService.searchFlights(destination);

        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(flights);
    }

    // Create a new flight - restricted to admins
    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build();
        }
        Flight createdFlight = flightService.createFlight(flight);
        return ResponseEntity.ok(createdFlight);
    }

    // Update an existing flight - restricted to admins
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build();
        }
        return flightService.updateFlight(id, flight)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a flight - restricted to admins
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build();
        }
        flightService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = flightService.getAllCities();
        return ResponseEntity.ok(cities);
    }

    // Get all passengers on a flight - restricted to agents
    @GetMapping("/{id}/passengers")
    public ResponseEntity<Set<Passenger>> getPassengers(@PathVariable Long id) {
        if (!authenticationService.getCurrentUser().isAgent()) {
            return ResponseEntity.status(403).build();
        }
        Set<Passenger> passengers = flightService.getPassengers(id);
        return ResponseEntity.ok(passengers);
    }
}
