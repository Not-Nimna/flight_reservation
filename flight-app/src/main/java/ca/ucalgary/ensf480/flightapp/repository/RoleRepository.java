package ca.ucalgary.ensf480.flightapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ucalgary.ensf480.flightapp.model.ERole;
import ca.ucalgary.ensf480.flightapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
