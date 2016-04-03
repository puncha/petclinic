package tk.puncha.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tk.puncha.models.Owner;

@Component
public class OwnerValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return Owner.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Owner owner = (Owner) target;
    if (owner.getAddress() != null) {
      int length = owner.getAddress().length();
      if (length < 5 || length > 80) {
        errors.rejectValue("address", "error.address.length");
      }
    }
  }
}
