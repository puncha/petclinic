package tk.puncha.restfulControllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

  @GetMapping
  @JsonView(PetJsonView.class)
  public List<PetType> getAll() {
    return petTypeRepository.getAll();
  }
}
