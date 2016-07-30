package tk.puncha.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id = -1;

  @Size(min = 3, max = 30, message = "{error.pet.name.length}")
  private String name;

  @Column(name = "birth_date")
  private Date birthDate;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private PetType type;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private Owner owner;

  @OneToMany(mappedBy = "pet", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  Set<Visit> visits = new HashSet<>();

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

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public PetType getType() {
    return type;
  }

  public void setType(PetType type) {
    this.type = type;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public Set<Visit> getVisits() {
    return visits;
  }
}
