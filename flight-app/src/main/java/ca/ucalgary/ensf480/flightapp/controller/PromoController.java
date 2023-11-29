package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Promo;
import ca.ucalgary.ensf480.flightapp.service.PromoService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promos")
public class PromoController {
    private final PromoService promoService;
    private final AuthenticationService authenticationService;

    @Autowired
    public PromoController(PromoService promoService, AuthenticationService authenticationService) {
        this.promoService = promoService;
        this.authenticationService = authenticationService;
    }

    // Get all promos
    @GetMapping
    public ResponseEntity<List<Promo>> getAllPromos() {
        return ResponseEntity.ok(promoService.getAllPromos());
    }

    // Get promo by ID
    @GetMapping("/{id}")
    public ResponseEntity<Promo> getPromoById(@PathVariable Long id) {
        return promoService.getPromoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new promo - restricted to admins
    @PostMapping
    public ResponseEntity<Promo> createPromo(@RequestBody Promo promo) {
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build();
        }
        Promo createdPromo = promoService.createPromo(promo);
        return ResponseEntity.ok(createdPromo);
    }
}
