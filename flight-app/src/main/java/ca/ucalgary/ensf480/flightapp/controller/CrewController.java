package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Crew;
import ca.ucalgary.ensf480.flightapp.service.CrewService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights/{flightId}/crew")
public class CrewController {

    private final CrewService crewService;
    private final AuthenticationService authenticationService;

    @Autowired
    public CrewController(CrewService crewService, AuthenticationService authenticationService) {
        this.crewService = crewService;
        this.authenticationService = authenticationService;
    }

    // Get crew members for a specific flight (only if admin)
    @GetMapping
    public ResponseEntity<List<Crew>> getCrewByFlight(@PathVariable Long flightId) {
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build(); // Forbidden access
        }
        List<Crew> crewMembers = crewService.getCrewByFlightId(flightId);
        return ResponseEntity.ok(crewMembers);
    }

}
