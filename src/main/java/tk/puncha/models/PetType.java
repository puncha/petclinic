package tk.puncha.models;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class PetType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id = -1;

  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
