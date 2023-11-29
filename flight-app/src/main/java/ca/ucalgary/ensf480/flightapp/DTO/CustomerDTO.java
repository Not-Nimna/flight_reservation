package ca.ucalgary.ensf480.flightapp.DTO;

public class CustomerDTO {
  private String name;
  private String email;

  public CustomerDTO() {
  }

  public CustomerDTO(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

}
