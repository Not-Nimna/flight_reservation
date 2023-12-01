package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.DTO.LoginDTO;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.UserRepository;

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
  
  
}
