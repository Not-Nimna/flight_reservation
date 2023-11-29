package ca.ucalgary.ensf480.flightapp.controller;

import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.model.UserType;
import ca.ucalgary.ensf480.flightapp.service.UserService;
import ca.ucalgary.ensf480.flightapp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    // Get all users (only if admin)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build(); // Forbidden access
        }
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Create a new user (accessible to all users)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Check if the current user has the privilege to create a new user
        if (!authenticationService.getCurrentUser().isAdmin()) {
            return ResponseEntity.status(403).build(); // Forbidden access
        }

        // Check if the user to be created is an admin or agent
        // Only admins can create other admins, and agents can't create other agents
        if ((user.isAdmin() && !authenticationService.getCurrentUser().isAdmin()) ||
                (user.isAgent() && authenticationService.getCurrentUser().isAgent())) {
            return ResponseEntity.status(403).build(); // Forbidden access
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
