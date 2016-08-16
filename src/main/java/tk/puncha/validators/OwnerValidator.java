package tk.puncha.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tk.puncha.models.Owner;

@Component
public class OwnerValidator implements Validator {

  public static final int MIN_ADDRESS_LENGTH = 5;
  public static final int MAX_ADDRESS_LENGTH = 80;

  @Override
  public boolean supports(Class<?> clazz) {
    return Owner.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Owner owner = (Owner) target;
    if (owner.getAddress() != null && owner.getAddress().length() != 0) {
      int length = owner.getAddress().length();
      if (length < MIN_ADDRESS_LENGTH || length > MAX_ADDRESS_LENGTH) {
        errors.rejectValue("address", "error.address.length");
      }
    }
  }
}
