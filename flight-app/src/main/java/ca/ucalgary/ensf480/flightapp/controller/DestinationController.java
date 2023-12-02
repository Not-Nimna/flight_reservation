package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Destination;
import ca.ucalgary.ensf480.flightapp.service.DestinationService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService, AuthenticationService authenticationService) {
        this.destinationService = destinationService;
    }

    // Get all destinations
    @GetMapping
    public ResponseEntity<List<Destination>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    // Get destination by ID
    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new destination - restricted to admins
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> createDestination(@RequestBody Destination destination) {

        Destination createdDestination = destinationService.createDestination(destination);
        return ResponseEntity.ok(createdDestination);
    }

    // Update an existing destination - restricted to admins
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Destination> updateDestination(@PathVariable Long id, @RequestBody Destination destination) {

        return destinationService.updateDestination(id, destination)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a destination - restricted to admins
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {

        destinationService.deleteDestination(id);
        return ResponseEntity.ok().build();
    }
}
