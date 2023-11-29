package ca.ucalgary.ensf480.flightapp.repository;

import ca.ucalgary.ensf480.flightapp.model.SeatClass;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.FlightSeatPrice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightSeatPriceRepository extends JpaRepository<FlightSeatPrice, SeatClass> {
      Optional<FlightSeatPrice> findByFlightAndSeatClass(Flight flight, SeatClass seatClass);

}
