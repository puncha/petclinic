package tk.puncha.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "VISITS")
public class Visit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String description;
  @Column(name = "VISIT_DATE")
  private Date visitDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PET_ID")
  private Pet pet;

  public Visit() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

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
