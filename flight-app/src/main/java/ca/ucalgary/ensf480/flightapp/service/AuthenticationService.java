/**
 * Authentication Service.
 * 
 * Provides functionality to retrieve the currently authenticated user from the security context.
 * Utilizes Spring Security's Authentication object to identify the current user and fetch their 
 * details from the UserRepository. Primarily used to integrate authentication information with 
 * user-specific operations within the flight booking application.
 * 
 * Returns null if no authenticated user is found, ensuring the integrity of user-specific actions.
 * 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 2, 2023
 */

package ca.ucalgary.ensf480.flightapp.service;

import ca.ucalgary.ensf480.flightapp.model.User;
import ca.ucalgary.ensf480.flightapp.repository.UserRepository;
import ca.ucalgary.ensf480.flightapp.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
            return userRepository.findByUsername(userDetails.getUsername())
                    .orElse(null);
        } else {
            return null;
        }
    }
}
