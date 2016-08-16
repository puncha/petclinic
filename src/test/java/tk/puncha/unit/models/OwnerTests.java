package tk.puncha.unit.models;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import tk.puncha.models.Owner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

public class OwnerTests {

  private static Validator validator;
  private Owner owner;

  @BeforeClass
  static public void beforeClass() {
    LocaleContextHolder.setLocale(Locale.ENGLISH);
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setFallbackToSystemLocale(false);
    messageSource.setBasenames("validation");
    messageSource.setDefaultEncoding("utf8");

    LocalValidatorFactoryBean localValidator = new LocalValidatorFactoryBean();
    localValidator.setValidationMessageSource(messageSource);
    localValidator.afterPropertiesSet();
    validator = localValidator;
  }

  @Before
  public void before() {
    this.owner = new Owner();
  }

  private void makeOwnerValid() {
    owner.setFirstName("firstname");
    owner.setLastName("lastname");
  }

  private void assertNameInvalid(String propertyName, String name, String message) {
    Set<ConstraintViolation<Owner>> violations = validator.validate(owner);
    assertEquals(1, violations.size());
    ConstraintViolation<Owner> violation = violations.iterator().next();
    assertEquals(propertyName, violation.getPropertyPath().toString());
    assertEquals(name, violation.getInvalidValue());
    assertEquals(message, violation.getMessage());
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
  public void shouldPropertiesFunctional() throws Exception {
    owner.setId(100);
    assertEquals(100, owner.getId());

    owner.setFirstName("first name");
    assertEquals("first name", owner.getFirstName());

    owner.setLastName("last name");
    assertEquals("last name", owner.getLastName());

    owner.setCity("city");
    assertEquals("city", owner.getCity());

    owner.setAddress("address");
    assertEquals("address", owner.getAddress());

    owner.setTelephone("tele");
    assertEquals("tele", owner.getTelephone());
  }

  @Test
  public void shouldDefaultOwnerNotValidate() throws Exception {
    Set<ConstraintViolation<Owner>> violations = validator.validate(owner);
    assertEquals(2, violations.size());
  }

  @Test
  public void shouldNotValidateIfLengthOfFirstNameLessThan3() throws Exception {
    makeOwnerValid();
    owner.setFirstName("12");
    assertNameInvalid("firstName", "12", "Size of First name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfLastNameLessThan3() throws Exception {
    makeOwnerValid();
    owner.setLastName("12");
    assertNameInvalid("lastName", "12", "Size of Last name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfFirstNameLargeThan30() throws Exception {
    makeOwnerValid();
    String name = String.format("%1$31s", "A");
    owner.setFirstName(name); // 31 A
    assertNameInvalid("firstName", name, "Size of First name must be between 3 and 30");
  }

  @Test
  public void shouldNotValidateIfLengthOfLastNameLargeThan30() throws Exception {
    makeOwnerValid();
    String name = String.format("%1$31s", "A");
    owner.setLastName(name); // 31 A
    assertNameInvalid("lastName", name, "Size of Last name must be between 3 and 30");
  }

}
