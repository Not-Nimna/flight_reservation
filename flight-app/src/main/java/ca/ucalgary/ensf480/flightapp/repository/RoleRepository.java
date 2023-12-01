package ca.ucalgary.ensf480.flightapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ca.ucalgary.ensf480.flightapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(String name);
}
