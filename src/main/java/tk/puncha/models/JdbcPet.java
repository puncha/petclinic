package tk.puncha.models;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

public class JdbcPet extends Pet {
  @NotNull
  @Transient
  private int typeId;

  @NotNull
  @Transient
  private int ownerId;    // JDBC only

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public int getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
  }
}
