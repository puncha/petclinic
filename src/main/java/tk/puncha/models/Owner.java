package tk.puncha.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tk.puncha.views.json.view.OwnerJsonView;
import tk.puncha.views.json.view.PetJsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Owners")
@NamedEntityGraphs({
    @NamedEntityGraph(name = "graph.Owners.lazyPets"),
    @NamedEntityGraph(name = "graph.Owners.withPets",
        attributeNodes = @NamedAttributeNode(value = "pets", subgraph = "pets"),
        subgraphs = @NamedSubgraph(name = "pets", attributeNodes = @NamedAttributeNode("type"))
    )
})
public class Owner {
  public Owner() {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id = -1;

  @Column(name = "first_name")
  @NotNull
  @Size(min = 3, max = 30, message = "{error.firstName.length}")
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  @Size(min = 3, max = 30, message = "{error.lastName.length}")
  private String lastName;

  private String address;
  private String city;
  private String telephone;

  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY,
      cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
      orphanRemoval = true)
  private List<Pet> pets = new ArrayList<>();

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  @XmlElement(name = "pet")
  @JacksonXmlElementWrapper(localName = "pets")
  @JacksonXmlProperty(localName = "pet")
  @JsonView(OwnerJsonView.WithPets.class)
  public List<Pet> getPets() {
    return pets;
  }
}
