package tk.puncha.restfulControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.puncha.models.Pet;
import tk.puncha.models.Visit;
import tk.puncha.repositories.PetRepository;
import tk.puncha.repositories.VisitRepository;

import javax.validation.Valid;

@RestController  // Includes @ResponseBody
@RequestMapping(path = "/api/pets/{petId}/visits", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestfulVisitController {
  private final VisitRepository visitRepository;
  private final PetRepository petRepository;

  @Autowired
  public RestfulVisitController(VisitRepository visitRepository, PetRepository petRepository) {
    this.visitRepository = visitRepository;
    this.petRepository = petRepository;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorInfo handleException(Exception exception) {
    return new ErrorInfo(String.format("Unhandled exception %s", exception.getMessage()));
  }

  @ModelAttribute("pet")
  public Pet getPet(@PathVariable int petId) {
    return petRepository.getById(petId);
  }

  @PostMapping
  public void create(Pet pet, @Valid @RequestBody Visit visit) {
    visit.setPet(pet);
    visitRepository.insert(visit);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable int id) {
    visitRepository.deleteById(id);
  }
}
