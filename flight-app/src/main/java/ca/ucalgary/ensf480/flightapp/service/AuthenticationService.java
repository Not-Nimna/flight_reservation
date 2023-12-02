package ca.ucalgary.ensf480.flightapp.service;

import org.springframework.stereotype.Service;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.model.ERole;

@Service
public class AuthenticationService {


    // This is a placeholder.
    public User getCurrentUser() {
      User user = new User("jon", "Jon.doe@gmail.com", "12345");
      // user.setRoles(ArraylList<ERole>(ERole.ROLE_ADMIN));
      return user;
        // Retrieve and return the currently authenticated user - to be completed
    }

}
