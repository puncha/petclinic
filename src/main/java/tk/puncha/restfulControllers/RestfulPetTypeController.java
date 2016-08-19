package tk.puncha.restfulControllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.puncha.models.PetType;
import tk.puncha.repositories.PetTypeRepository;
import tk.puncha.views.json.view.PetJsonView;

import java.util.List;

@RestController  // Includes @ResponseBody
@RequestMapping(path = "/api/petTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestfulPetTypeController {
  private final PetTypeRepository petTypeRepository;

  @Autowired
  public RestfulPetTypeController(PetTypeRepository petTypeRepository) {
    this.petTypeRepository = petTypeRepository;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorInfo handleException(Exception exception) {
    return new ErrorInfo(String.format("Unhandled exception %s", exception.getMessage()));
  }

  @GetMapping
  @JsonView(PetJsonView.class)
  public List<PetType> query() {
    return petTypeRepository.getAll();
  }
}
