package tk.puncha.restfulControllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.puncha.models.Pet;
import tk.puncha.repositories.PetRepository;
import tk.puncha.views.json.view.PetJsonView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController  // Includes @ResponseBody
@RequestMapping(path = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestfulPetController {
  private final PetRepository petRepository;

  @Autowired
  public RestfulPetController(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorInfo handleException(Exception exception) {
    return new ErrorInfo(String.format("Unhandled exception %s", exception.getMessage()));
  }

  @GetMapping
  @JsonView(PetJsonView.class)
  public List<Pet> getAll() {
    return petRepository.getAll();
  }

  @GetMapping("{id}")
  @JsonView(PetJsonView.class)
  public ResponseEntity<Pet> get(@PathVariable int id) {
    Pet pet = petRepository.getById(id);
    if (pet == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return ResponseEntity.ok(pet);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@Valid @RequestBody Pet pet, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new RuntimeException("Invalid data.");
    }
    petRepository.insert(pet);
  }

  @PostMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@Valid @RequestBody Pet pet, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new RuntimeException("Invalid data.");
    }
    petRepository.update(pet);
  }

  @DeleteMapping("{id}")
  public ResponseEntity delete(@PathVariable int id) {
    try {
      petRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
