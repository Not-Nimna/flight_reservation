package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
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

  // Needs to be fixed
  public User createUser(User user) {
    return userRepository.save(user);
  }

  // Needs to be fixed
  public Optional<User> updateUser(Long id, User userDetails) {
    return userRepository.findById(id)
        .map(user -> {
          // Map the updated details to the existing user entity
          user.setEmail(userDetails.getEmail());
          user.setPassword(userDetails.getPassword());
          return userRepository.save(user);
        });
  }

}
