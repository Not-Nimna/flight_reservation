package ca.ucalgary.ensf480.flightapp.DTO;

import java.util.Set;

public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private Set<String> role;
    
    public SignupRequest() {
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Set<String> role) {
        this.role = role;
    }
    public Set<String> getRole() {
      return role;
    }
}