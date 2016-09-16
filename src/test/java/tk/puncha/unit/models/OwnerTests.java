package tk.puncha.unit.models;

import org.junit.Before;
import org.junit.Test;
import tk.puncha.TestUtil;
import tk.puncha.models.Owner;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

public class OwnerTests {

  private Owner owner;

  @Before
  public void before() {
    this.owner = new Owner();
  }

  private void makeValid() {
    owner.setFirstName("firstname");
    owner.setLastName("lastname");
  }

  @Test
  public void shouldDefaultOwnerWellInitialized() throws Exception {
    assertEquals(-1, owner.getId());
    assertNull(owner.getFirstName());
    assertNull(owner.getLastName());
    assertNull(owner.getAddress());
    assertNull(owner.getCity());
    assertNull(owner.getTelephone());
    assertNotNull(owner.getPets());
  }

  @Test
  public void shouldDefaultOwnerNotValidate() throws Exception {
    Set<ConstraintViolation<Owner>> violations = TestUtil.createValidator().validate(owner);
    assertEquals(2, violations.size());
  }

  @Test
  public void shouldNotValidateIfLengthOfFirstNameLessThan3() throws Exception {
    makeValid();
    owner.setFirstName("12");
    TestUtil.assertViolation(owner, "firstName", "12", "Size of First name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfLastNameLessThan3() throws Exception {
    makeValid();
    owner.setLastName("12");
    TestUtil.assertViolation(owner, "lastName", "12", "Size of Last name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfFirstNameLargeThan30() throws Exception {
    makeValid();
    final String name = String.format("%1$31s", "A");
    owner.setFirstName(name); // 31 A
    TestUtil.assertViolation(owner, "firstName", name, "Size of First name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfLastNameLargeThan30() throws Exception {
    makeValid();
    final String name = String.format("%1$31s", "A");
    owner.setLastName(name); // 31 A
    TestUtil.assertViolation(owner, "lastName", name, "Size of Last name must be between 3 and 30");
  }

}
