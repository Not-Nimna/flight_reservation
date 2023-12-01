package ca.ucalgary.ensf480.flightapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import ca.ucalgary.ensf480.flightapp.DTO.LoginDTO;
import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/public")
public class SessionsController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    //@Autowired
    //private RoleRepository roleRepository;
    @Autowired

    private PasswordEncoder passwordEncoder;


  @RequestMapping("/help")
  public String getHelp() {
    return "help";
  }

  @PostMapping("/login")
  public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
      Authentication authentication = authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return new ResponseEntity<>("User login successfully!...", HttpStatus.OK);
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request){
      HttpSession session = request.getSession();
      session.invalidate();
      SecurityContext securityContext = SecurityContextHolder.getContext();
      securityContext.setAuthentication(null);
      return "redirect:/login";
  }




	public record LoginRequest(String username, String password) {
	}

}