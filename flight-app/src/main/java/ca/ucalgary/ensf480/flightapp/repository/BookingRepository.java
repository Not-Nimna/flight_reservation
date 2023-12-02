package ca.ucalgary.ensf480.flightapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ucalgary.ensf480.flightapp.model.Booking;
import ca.ucalgary.ensf480.flightapp.model.Flight;
import ca.ucalgary.ensf480.flightapp.model.Seat;
import ca.ucalgary.ensf480.flightapp.model.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  Optional<Booking> findByCancellationCode(String cancellationCode);

  List<Booking> findByFlightId(Long flightId);

  Optional<Booking> findBySeatAndFlight(Seat seat, Flight flight);

  List<Booking> findByFlight(Flight flight);


  List<Booking> findBySeat(Seat seat);

  List<Booking> findByUserId(Long userId);

  List<Booking> findByUser(User user);

}