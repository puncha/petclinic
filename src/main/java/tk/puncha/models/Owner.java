package tk.puncha.models;

import javax.validation.constraints.Size;

public class Owner {
  private int id = -1;

  @Size(min = 3, max=30, message = "{error.firstName.length}")
  private String firstName;

  @Size(min = 3, max = 30, message = "{error.lastName.length}")
  private String lastName;

  private String address;
  private String city;
  private String telephone;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
}
