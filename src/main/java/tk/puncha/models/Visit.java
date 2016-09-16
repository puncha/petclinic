package tk.puncha.models;

import com.fasterxml.jackson.annotation.JsonView;
import tk.puncha.views.json.view.OwnerJsonView;
import tk.puncha.views.json.view.PetJsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "VISITS")
public class Visit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id = -1;
  private String description;

  @Column(name = "VISIT_DATE")
  @NotNull
  private Date visitDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PET_ID")
  private Pet pet;

  public Visit() {
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @JsonView({OwnerJsonView.Default.class, PetJsonView.class})
  public Date getVisitDate() {
    return visitDate;
  }

  public void setVisitDate(Date visitDate) {
    this.visitDate = visitDate;
  }

  public Pet getPet() {
    return pet;
  }

  public void setPet(Pet pet) {
    this.pet = pet;
  }
}
