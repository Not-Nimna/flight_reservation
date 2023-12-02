package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Passenger;
import ca.ucalgary.ensf480.flightapp.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    PassengerService passengerService;

    // Get all passengers - restricted to agents
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {

        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    // Get passenger by ID - restricted to agents
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {

        return passengerService.getPassengerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a passenger - restricted to agents
    @DeleteMapping("/{id}")
    public ResponseEntity<Passenger> deletePassenger(@PathVariable Long id) {

        passengerService.deletePassenger(id);
        return ResponseEntity.ok().build();
    }
}
