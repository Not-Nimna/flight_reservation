package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.Promo;
import ca.ucalgary.ensf480.flightapp.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", methods = { RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RequestMapping("/api")
public class PromoController {

    @Autowired
    PromoService promoService;

    // Get all promos
    @GetMapping("/public/promos")
    public ResponseEntity<List<Promo>> getAllPromos() {
        return ResponseEntity.ok(promoService.getAllPromos());
    }

    // Get promo by ID
    @GetMapping("/public/promos/{id}")
    public ResponseEntity<Promo> getPromoById(@PathVariable Long id) {
        return promoService.getPromoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new promo - restricted to admins
    @PostMapping("/promos")
    public ResponseEntity<Promo> createPromo(@RequestBody Promo promo) {

        Promo createdPromo = promoService.createPromo(promo);
        return ResponseEntity.ok(createdPromo);
    }
}
