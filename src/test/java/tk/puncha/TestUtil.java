package tk.puncha;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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


  public static byte[] objectToJsonBytes(Object object) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }

  public static String objectToJsonString(Object object) throws JsonProcessingException {
    return objectToJsonString(object, null);
  }

  public static String objectToJsonString(Object object, Class viewClass) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    if (viewClass != null) {
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
      mapper.setConfig(mapper.getSerializationConfig().withView(viewClass));
    }
    return mapper.writeValueAsString(object);
  }
}
