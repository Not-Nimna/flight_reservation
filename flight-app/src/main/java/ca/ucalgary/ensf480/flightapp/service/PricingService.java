package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.*;
import ca.ucalgary.ensf480.flightapp.repository.FlightSeatPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PricingService {

    private final FlightSeatPriceRepository flightSeatPriceRepository;

    @Autowired
    public PricingService(FlightSeatPriceRepository flightSeatPriceRepository) {
        this.flightSeatPriceRepository = flightSeatPriceRepository;
    }

    // Update this funciton to take in the the user.
    // It should check if there is a promo for the given flight, and if the user is
    // not null
    // and then apply the promo.
    // to improve performance, it should probably take in the promo as a param to
    // preven n+1 queries.
    // to improve performance, it should probably take in the promo as a param to preven n+1 queries.
    public BigDecimal calculatePrice(Seat seat, Flight flight) {
        // Retrieve the base price for the seat class of this seat on this flight
        return flightSeatPriceRepository.findByFlightAndSeatClass(flight, seat.getSeatClass())
                .map(FlightSeatPrice::getPrice)
                .orElseThrow(() -> new RuntimeException("Base price not found for seat class: "
                        + seat.getSeatClass() + " on flight: " + flight.getId()));
    }
    public BigDecimal calculatePromoPrice(Flight flight, User user, BigDecimal currentPrice) {
        Promo promotion = flight.getPromo();
        boolean isUserRegistered = user != null;

        if (promotion != null && isUserRegistered) {
            double discount = promotion.getDiscount();

            currentPrice = applyPromotion(currentPrice, BigDecimal.valueOf(discount));
        }
        return currentPrice;
    }

    public BigDecimal applyPromotion(BigDecimal originalPrice, BigDecimal discount) {
        // Apply the promotion discount to the original price
        BigDecimal discountedPrice = originalPrice.subtract(originalPrice.multiply(discount));
        // Ensure the discounted price is non-negative
        return discountedPrice.max(BigDecimal.ZERO);
    }

    // Additional methods as needed
}
