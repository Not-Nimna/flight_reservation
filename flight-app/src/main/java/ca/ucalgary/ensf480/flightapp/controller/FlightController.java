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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightController {

    private final FlightService flightService;
    private final BookingService bookingService;
    private final AuthenticationService authenticationService;

    @Autowired
    public FlightController(FlightService flightService, BookingService bookingService, AuthenticationService authenticationService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.authenticationService = authenticationService;
    }

    // Get all flights - accessible to all users
    @GetMapping("/public/flights")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    // GET endpoint to retrieve seat map for a flight
    @GetMapping("/public/flights/{id}/seatMap")
    public ResponseEntity<List<SeatBookingDTO>> getSeatMap(@PathVariable Long id) {
        List<SeatBookingDTO> seatMap = bookingService.getSeatMap(id);
        return ResponseEntity.ok(seatMap);
    }
    
    // Get a single flight by ID - accessible to all users
    @GetMapping("/public/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search flights - accessible to all users
    @GetMapping("/public/flights/search")
    public ResponseEntity<List<Flight>> searchFlights(@RequestParam String query) {
        return ResponseEntity.ok(flightService.searchFlights(query));
    }

    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String hello(){
        return "Hello from admin!";
    }

    @GetMapping("/agent/hello")
    @PreAuthorize("hasRole('AGENT')")
    public String hello2(){
        return "Hello from agent!";
    }

    // Create a new flight - restricted to admins
    @PostMapping("/admin/flights")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {

        Flight createdFlight = flightService.createFlight(flight);
        return ResponseEntity.ok(createdFlight);
    }

    // Update an existing flight - restricted to admins
    @PutMapping("/admin/flights/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {

        return flightService.updateFlight(id, flight)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a flight - restricted to admins
    @DeleteMapping("/admin/flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {

        flightService.deleteFlight(id);
        return ResponseEntity.ok().build();
    }

}
