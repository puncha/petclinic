package tk.puncha.unit.validators;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import tk.puncha.models.Owner;
import tk.puncha.validators.OwnerValidator;

import static org.junit.Assert.*;

public class OwnerValidatorTests {

  static private OwnerValidator ownerValidator;
  private Owner owner;
  private Errors errors;

  @BeforeClass
  public static void beforeClass() {
    ownerValidator = new OwnerValidator();
  }

  @Before
  public void before() {
    this.owner = new Owner();
    this.errors = new BeanPropertyBindingResult(owner, "owner");
  }

  private void validate() {
    ownerValidator.validate(owner, errors);
  }

  private void assertIsValid() {
    assertFalse(errors.hasErrors());
  }

  private void assertIsInvalid() {
    assertTrue(errors.hasErrors());
  }

  @Test
  public void shouldSupportNotValidateObjectClass() throws Exception {
    assertFalse(ownerValidator.supports(Object.class));
  }

  @Test
  public void shouldSupportValidateOwnerClass() throws Exception {
    assertTrue(ownerValidator.supports(Owner.class));
  }

  @Test
  public void shouldSupportTypesInheritFromOwner() throws Exception {
    assertTrue(ownerValidator.supports(new Owner(){}.getClass()));  // anonymous class
  }

  @Test
  public void shouldMinLengthOfAddressBe5() throws Exception {
    assertEquals(5, OwnerValidator.MIN_ADDRESS_LENGTH);
  }

  @Test
  public void shouldMaxLengthOfAddressBe80() throws Exception {
    assertEquals(80, OwnerValidator.MAX_ADDRESS_LENGTH);
  }

  @Test
  public void shouldNotValidateIfLengthOfAddressLessThen5() throws Exception {
    owner.setAddress("1234");
    validate();
    assertIsInvalid();
    assertTrue(errors.hasFieldErrors("address"));
    assertEquals("1234", errors.getFieldError("address").getRejectedValue());
    assertEquals("error.address.length", errors.getFieldError("address").getCode());
  }

  @Test
  public void shouldNotValidateIfLengthOfAddressLargerThan80() throws Exception {
    owner.setAddress(String.format("%1$81s", "A")); // 81 A
    validate();
    assertIsInvalid();
    assertTrue(errors.hasFieldErrors("address"));
    assertEquals("error.address.length", errors.getFieldError("address").getCode());
  }

  @Test
  public void shouldValidateIfLengthOfAddressBetween5And80() throws Exception {
    owner.setAddress("1234567890");
    validate();
    assertIsValid();
  }

  @Test
  public void shouldValidateIfAddressIsNull() throws Exception {
    validate();
    assertIsValid();
  }
}
