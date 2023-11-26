package ca.ucalgary.ensf480.flightapp.repository;

import ca.ucalgary.ensf480.flightapp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
