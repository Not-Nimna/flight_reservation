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
