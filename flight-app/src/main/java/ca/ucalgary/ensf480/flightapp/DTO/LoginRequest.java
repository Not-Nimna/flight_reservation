 /** 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 1, 2023
 */

package ca.ucalgary.ensf480.flightapp.DTO;


public class LoginRequest {
    private String username;
    private String password;   
    public LoginRequest() {      }   
    public String getUsername() {      
        return username;   
    }   
    public void setUsername(String username) {      
        this.username = username;   
    }   
    public String getPassword() {      
        return password;   
    }   
    public void setPassword(String password) {      
        this.password = password;   
    }
}