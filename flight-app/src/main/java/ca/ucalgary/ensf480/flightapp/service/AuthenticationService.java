package ca.ucalgary.ensf480.flightapp.service;

import org.springframework.stereotype.Service;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.model.UserType;

@Service
public class AuthenticationService {

  // This is a placeholder.
  public User getCurrentUser() {
    return new User("Jon.doe@gmail.com", "12345", UserType.ADMIN);
    // Retrieve and return the currently authenticated user - to be completed
  }
}
