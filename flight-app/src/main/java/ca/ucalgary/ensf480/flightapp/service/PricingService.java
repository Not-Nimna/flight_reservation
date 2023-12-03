/**
 * Payment Service.
 * 
 * Manages the processing of payments for the flight booking application. Simulates interaction 
 * with a payment provider and handles creation of payment records. Provides methods to create 
 * a new payment instance based on payment details, price, and customer information.
 * 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 1, 2023
 */

package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.*;
import ca.ucalgary.ensf480.flightapp.repository.FlightSeatPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PricingService {

    @Autowired
    FlightSeatPriceRepository flightSeatPriceRepository;

    public BigDecimal calculatePrice(Seat seat, Flight flight, User user) {
        // Retrieve the base price for the seat class of this seat on this flight
        BigDecimal price = flightSeatPriceRepository.findByFlightAndSeatClass(flight, seat.getSeatClass())
                .map(FlightSeatPrice::getPrice)
                .orElseThrow(() -> new RuntimeException("Base price not found for seat class: "
                        + seat.getSeatClass() + " on flight: " + flight.getId()));
        return calculatePromoPrice(flight, user, price);
    }
    public BigDecimal calculatePromoPrice(Flight flight, User user, BigDecimal currentPrice) {
        Promo promotion = flight.getPromo();

        if (promotion != null && user != null) {
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

}
