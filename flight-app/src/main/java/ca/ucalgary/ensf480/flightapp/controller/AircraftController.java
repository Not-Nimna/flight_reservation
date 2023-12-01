package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Aircraft;
import ca.ucalgary.ensf480.flightapp.service.AircraftService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AircraftController(AircraftService aircraftService, AuthenticationService authenticationService) {
        this.aircraftService = aircraftService;
        this.authenticationService = authenticationService;
    }

    // Get all aircraft - restricted to admins
    @GetMapping
    public ResponseEntity<List<Aircraft>> getAllAircraft() {

        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    // Get aircraft by ID - restricted to admins
    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {

        return aircraftService.getAircraftById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new aircraft - restricted to admins
    @PostMapping
    public ResponseEntity<Aircraft> createAircraft(@RequestBody Aircraft aircraft) {

        Aircraft createdAircraft = aircraftService.createAircraft(aircraft);
        return ResponseEntity.ok(createdAircraft);
    }

    // Update an existing aircraft - restricted to admins
    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {

        return aircraftService.updateAircraft(id, aircraft)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete an aircraft - restricted to admins
    @DeleteMapping("/{id}")
    public ResponseEntity<Aircraft> deleteAircraft(@PathVariable Long id) {

        aircraftService.deleteAircraft(id);
        return ResponseEntity.ok().build();
    }    
}
