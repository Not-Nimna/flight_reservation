package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Passenger;
import ca.ucalgary.ensf480.flightapp.service.PassengerService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;
    private final AuthenticationService authenticationService;

    @Autowired
    public PassengerController(PassengerService passengerService, AuthenticationService authenticationService) {
        this.passengerService = passengerService;
        this.authenticationService = authenticationService;
    }

    // Get all passengers - restricted to agents
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        if (!authenticationService.getCurrentUser().isAgent()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    // Get passenger by ID - restricted to agents
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        if (!authenticationService.getCurrentUser().isAgent()) {
            return ResponseEntity.status(403).build();
        }
        return passengerService.getPassengerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a passenger - restricted to agents
    @DeleteMapping("/{id}")
    public ResponseEntity<Passenger> deletePassenger(@PathVariable Long id) {
        if (!authenticationService.getCurrentUser().isAgent()) {
            return ResponseEntity.status(403).build();
        }
        passengerService.deletePassenger(id);
        return ResponseEntity.ok().build();
    }
}
