package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Crew;
import ca.ucalgary.ensf480.flightapp.service.CrewService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights/{flightId}/crew")
public class CrewController {

    private final CrewService crewService;

    @Autowired
    public CrewController(CrewService crewService, AuthenticationService authenticationService) {
        this.crewService = crewService;
    }

    // Get crew members - restricted to admins
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Crew>> getCrewByFlight(@PathVariable Long flightId) {

        List<Crew> crewMembers = crewService.getCrewByFlightId(flightId);
        return ResponseEntity.ok(crewMembers);
    }

}
