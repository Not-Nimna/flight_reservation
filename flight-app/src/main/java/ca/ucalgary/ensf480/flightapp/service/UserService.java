package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.DTO.LoginRequest;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin(origins = "http://localhost:3000")
public class UserService {

  private UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> findById(Long userId) {
    return userRepository.findById(userId);
  }


  public User getUserByEmail(String email){
      User user = userRepository.findByEmail(email);
      return user;
  }
  

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(User user) {

    return userRepository.save(user);

  }

  // public User currentUser() {
  // return userRepository.findByIsCurrent(true);
  // }

  public Optional<User> updateUser(Long id, User userDetails) {
    return userRepository.findById(id)
        .map(user -> {
          // Map the updated details to the existing user entity
          user.setEmail(userDetails.getEmail());
          user.setPassword(userDetails.getPassword());
          user.setUserType(userDetails.getUserType());
          user.setDestination(userDetails.getDestination());
          return userRepository.save(user);
        });
  }

}
