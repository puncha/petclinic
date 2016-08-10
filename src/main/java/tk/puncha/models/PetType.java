package tk.puncha.models;

import com.fasterxml.jackson.annotation.JsonView;
import tk.puncha.views.json.view.OwnerJsonView;
import tk.puncha.views.json.view.PetJsonView;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class PetType {
  public PetType() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id = -1;

  private String name;

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
