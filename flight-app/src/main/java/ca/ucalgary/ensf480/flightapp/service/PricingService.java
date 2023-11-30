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
    // It should check if there is a promo for the given flight, and if the user is not null
    // and then apply the promo.
    // to improve performance, it should probably take in the promo as a param to preven n+1 queries.
    public BigDecimal calculatePrice(Seat seat, Flight flight, User user) {
        // Retrieve the base price for the seat class of this seat on this flight
        return flightSeatPriceRepository.findByFlightAndSeatClass(flight, seat.getSeatClass())
                                        .map(FlightSeatPrice::getPrice)
                                        .orElseThrow(() -> new RuntimeException("Base price not found for seat class: " 
                                                     + seat.getSeatClass() + " on flight: " + flight.getId()));
    }

    // Additional methods as needed
}
