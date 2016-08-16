package tk.puncha.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import tk.puncha.views.json.view.OwnerJsonView;
import tk.puncha.views.json.view.PetJsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
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
  @NotNull
  private PetType type;

  @ManyToOne
  @JoinColumn(name = "owner_id")
  @NotNull
  private Owner owner;

  @OneToMany(mappedBy = "pet", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
  private Set<Visit> visits = new HashSet<>();

  @JsonView({PetJsonView.class})
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

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public PetType getType() {
    return type;
  }

  public void setType(PetType type) {
    this.type = type;
  }

  @XmlTransient
  @JsonView({PetJsonView.class})
  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  @JacksonXmlElementWrapper(localName = "visits")
  @JacksonXmlProperty(localName = "visit")
  public Set<Visit> getVisits() {
    return visits;
  }
}
