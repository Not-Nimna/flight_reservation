package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@PreAuthorize("hasRole('ADMIN')")
public class SeatController {

    @Autowired
    SeatService seatService;

    // Get all seats for a specific aircraft
    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<Seat>> getSeatsByAircraft(@PathVariable Long aircraftId) {
        List<Seat> seats = seatService.getSeatsByAircraftId(aircraftId);
        return ResponseEntity.ok(seats);
    }

    // Get a single seat by ID
    @GetMapping("/{seatId}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long seatId) {
        return seatService.getSeatById(seatId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new seat
    @PostMapping
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        Seat createdSeat = seatService.createSeat(seat);
        return ResponseEntity.ok(createdSeat);
    }

    // Update an existing seat
    @PutMapping("/{seatId}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long seatId, @RequestBody Seat seat) {
        return seatService.updateSeat(seatId, seat)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a seat
    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long seatId) {
        seatService.deleteSeat(seatId);
        return ResponseEntity.ok().build();
    }
}
