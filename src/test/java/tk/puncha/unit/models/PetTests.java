package tk.puncha.unit.models;

import org.junit.Before;
import org.junit.Test;
import tk.puncha.TestUtil;
import tk.puncha.models.Owner;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

public class PetTests {

  private Pet pet;

  @Before
  public void before() {
    this.pet = new Pet();
  }

  private void makeValid() {
    pet.setName("petName");
    pet.setOwner(new Owner());
    pet.setType(new PetType());
  }

  @Test
  public void shouldDefaultPetWellInitialized() throws Exception {
    assertEquals(-1, pet.getId());
    assertNull(pet.getName());
    assertNull(pet.getType());
    assertNull(pet.getBirthDate());
    assertNotNull(pet.getVisits());
    assertTrue(pet.getVisits().isEmpty());
  }

  @Test
  public void shouldDefaultPetNotValidate() throws Exception {
    Set<ConstraintViolation<Pet>> violations = TestUtil.createValidator().validate(pet);
    assertEquals(2, violations.size()); // owner, type
  }

  @Test
  public void shouldNotValidateIfLengthOfNameLessThan3() throws Exception {
    makeValid();
    pet.setName("12");
    TestUtil.assertViolation(pet, "name", "12", "Size of name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfNameLargeThan30() throws Exception {
    makeValid();
    String name = String.format("%1$31s", "A");
    pet.setName(name); // 31 A
    TestUtil.assertViolation(pet, "name", name, "Size of name must be between 3 and 30");
  }
}
