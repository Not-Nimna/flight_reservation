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
import ca.ucalgary.ensf480.flightapp.service.FlightService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import ca.ucalgary.ensf480.flightapp.service.BookingService;

import ca.ucalgary.ensf480.flightapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController

@RequestMapping("/api")
public class FlightController {

    @Autowired
    FlightService flightService;

    @Autowired
    BookingService bookingService;

    @Autowired
    AuthenticationService authenticationService;


    // Get all flights - accessible to all users
    @GetMapping("/public/flights")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    // GET endpoint to retrieve seat map for a flight
    @GetMapping("/public/flights/{id}/seatMap")
    public ResponseEntity<List<SeatBookingDTO>> getSeatMap(@PathVariable Long id) {
        User user = authenticationService.getCurrentUser();
        List<SeatBookingDTO> seatMap = bookingService.getSeatMap(id, user);
        return ResponseEntity.ok(seatMap);
    }

    // Get a single flight by ID - accessible to all users
    // Needs to be fixed
    @GetMapping("/public/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> optionalFlight = flightService.getFlightById(id);
        if (optionalFlight.isPresent()) {
            return ResponseEntity.ok(optionalFlight.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Search flights - accessible to all users
    @GetMapping("/public/search/{destination}")
    public ResponseEntity<List<FlightDTO>> searchFlights(@PathVariable String destination) {
        List<FlightDTO> flights = flightService.searchFlights(destination);

        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flights);
    }

    // Create a new flight - restricted to admins
    @PostMapping("/flights")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {

        Flight createdFlight = flightService.createFlight(flight);
        return ResponseEntity.ok(createdFlight);
    }

    // Update an existing flight - restricted to admins
    @PutMapping("/flights/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a flight - restricted to admins
    @DeleteMapping("/flights/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/cities")
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = flightService.getAllCities();
        return ResponseEntity.ok(cities);
    }
}
