package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.SeatClass;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.FlightSeatPrice;
import ca.ucalgary.ensf480.flightapp.repository.FlightSeatPriceRepository;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class FlightSeatPriceService {

    private final FlightSeatPriceRepository flightSeatPriceRepository;

    public FlightSeatPriceService(FlightSeatPriceRepository flightSeatPriceRepository) {
        this.flightSeatPriceRepository = flightSeatPriceRepository;
    }

public BigDecimal getPriceForSeatClass(Flight flight, SeatClass seatClass) {
        return flightSeatPriceRepository.findByFlightAndSeatClass(flight, seatClass)
                .map(FlightSeatPrice::getPrice)
                .orElseThrow(() -> new RuntimeException("Price not found for seat class: " + seatClass + " for flight: " + flight.getId()));
    }


}
