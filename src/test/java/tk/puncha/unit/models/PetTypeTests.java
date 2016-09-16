package tk.puncha.unit.models;

import org.junit.Before;
import org.junit.Test;
import tk.puncha.models.PetType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PetTypeTests {

  private PetType petType;

  @Before
  public void before() {
    this.petType = new PetType();
  }

  @Test
  public void shouldDefaultVisitWellInitialized() throws Exception {
    assertEquals(-1, petType.getId());
    assertNull(petType.getName());
  }

}
