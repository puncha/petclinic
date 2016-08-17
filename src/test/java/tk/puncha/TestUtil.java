package tk.puncha;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public abstract class TestUtil {

  public static Validator createValidator() {
    LocaleContextHolder.setLocale(Locale.ENGLISH);
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setFallbackToSystemLocale(false);
    messageSource.setBasenames("validation");
    messageSource.setDefaultEncoding("utf8");

    LocalValidatorFactoryBean localValidator = new LocalValidatorFactoryBean();
    localValidator.setValidationMessageSource(messageSource);
    localValidator.afterPropertiesSet();
    return localValidator;
  }


  public static <T> void assertViolation(
      T objToValidate, String property, Object invalidValue, String message) {
    Set<ConstraintViolation<T>> violations = createValidator().validate(objToValidate);
    assertEquals(1, violations.size());
    ConstraintViolation<T> violation = violations.iterator().next();
    assertEquals(property, violation.getPropertyPath().toString());
    assertEquals(invalidValue, violation.getInvalidValue());
    assertEquals(message, violation.getMessage());
  }
}
