 /** 
 * @author Marshal Kalynchuk
 * @ucid 30153895
 * @date Dec 1, 2023
 */

package ca.ucalgary.ensf480.flightapp.DTO;

public class MessageResponse {
  private String message;

  public MessageResponse(String string) {
    this.message = string;
  }

  public String getMessage() {
    return message;
  }
  public void setMessage(String string) {
    this.message = string;
  }
  
}
