package tk.puncha.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;

import java.text.ParseException;
import java.util.Locale;

@Component
public class OwnerFormatter implements Formatter<Owner> {

  private final OwnerRepository ownerRepository;

  @Autowired
  public OwnerFormatter(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
  }

  @Override
  public Owner parse(String text, Locale locale) throws ParseException {
    return ownerRepository.getById(Integer.parseInt(text));
  }

  @Override
  public String print(Owner object, Locale locale) {
    return null;
  }
}
