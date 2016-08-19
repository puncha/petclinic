package tk.puncha.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import tk.puncha.models.PetType;
import tk.puncha.repositories.PetTypeRepository;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

  @Autowired
  PetTypeRepository petTypeRepository;

  @Override
  public PetType parse(String text, Locale locale) throws ParseException {
    return petTypeRepository.getById(Integer.parseInt(text, 10));
  }

  @Override
  public String print(PetType object, Locale locale) {
    if (object != null) return object.getName();
    return null;
  }
}
