package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.Promo;
import ca.ucalgary.ensf480.flightapp.repository.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromoService {

    private final PromoRepository promoRepository;

    @Autowired
    public PromoService(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    public Optional<Promo> getPromoById(Long id) {
        return promoRepository.findById(id);
    }

    public List<Promo> getAllPromos() {
        return promoRepository.findAll();
    }

    public List<Promo> searchPromos(String query) {
        return null; // Replace with actual search logic
    }

    public Promo createPromo(Promo promo) {
        return promoRepository.save(promo);
    }
}
