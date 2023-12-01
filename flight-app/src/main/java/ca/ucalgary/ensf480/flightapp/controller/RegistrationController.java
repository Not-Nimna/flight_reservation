package ca.ucalgary.ensf480.flightapp.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ca.ucalgary.ensf480.flightapp.DTO.LoginDTO;
import ca.ucalgary.ensf480.flightapp.DTO.SignupDTO;
import ca.ucalgary.ensf480.flightapp.model.Role;
import ca.ucalgary.ensf480.flightapp.model.User;

import ca.ucalgary.ensf480.flightapp.repository.RoleRepository;
import ca.ucalgary.ensf480.flightapp.repository.UserRepository;


@RestController
@RequestMapping("/api/public")
public class RegistrationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO){
            // checking for username exists in a database

            // checking for email exists in a database
            if(userRepository.existsByEmail(signupDTO.getEmail())){
                return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
            }
            // creating user object
            User user = new User();
            user.setEmail(signupDTO.getEmail());
            user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
            
            Role roles = roleRepository.findByName("ROLE_USER").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
            return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
        }

}
