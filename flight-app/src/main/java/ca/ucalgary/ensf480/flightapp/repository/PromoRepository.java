package ca.ucalgary.ensf480.flightapp.repository;

import ca.ucalgary.ensf480.flightapp.model.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
}
