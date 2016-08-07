package tk.puncha.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.formatters.OwnerFormatter;
import tk.puncha.formatters.PetTypeFormatter;
import tk.puncha.models.Owner;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.repositories.PetRepository;
import tk.puncha.views.json.view.PetJsonView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pets")
@SessionAttributes("id")
public class PetController extends ControllerBase {

  private static final Logger logger = LoggerFactory.getLogger(PetController.class);

  private final PetRepository petRepository;
  private final OwnerRepository ownerRepository;
  private final OwnerFormatter ownerFormatter;
  private final PetTypeFormatter petTypeFormatter;


  @Autowired
  public PetController(OwnerRepository ownerRepository, PetRepository petRepository, OwnerFormatter ownerFormatter, PetTypeFormatter petTypeFormatter) {
    this.ownerRepository = ownerRepository;
    this.petRepository = petRepository;
    this.ownerFormatter = ownerFormatter;
    this.petTypeFormatter = petTypeFormatter;
  }

  @InitBinder("pet")
  void initPetBinder(WebDataBinder binder) {
    binder.addCustomFormatter(ownerFormatter);
    binder.addCustomFormatter(petTypeFormatter);
    binder.setDisallowedFields("id");
  }

  @ModelAttribute("types")
  List<PetType> getPetTypes() {
    logger.debug("getPetTypes()");
    return petRepository.getAllTypes();
  }

  @ModelAttribute("owners")
  List<Owner> getOwners() {
    logger.debug("getOwners()");
    return ownerRepository.getAllOwners();
  }

  @GetMapping(value = {"", "/index", "/default"}, produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView htmlIndex() {
    logger.debug("index()");
    List<Pet> petViews = petRepository.getAllPets();
    return new ModelAndView("pet/index", "pets", petViews);
  }

  //-----------
  // Restful to return XML & JSON
  //-----------
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @JsonView(PetJsonView.class)
  @ResponseBody
  private List<Pet> restfulIndex() {
    return petRepository.getAllPets();
  }

  @GetMapping("{id}")
  public ModelAndView view(@PathVariable int id) {
    logger.debug("view()");
    Pet pet = petRepository.getPetById(id);
    ensureExist(pet);
    return createFormModelView(pet, FormMode.Readonly);
  }

  @GetMapping("{id}/edit")
  public ModelAndView edit(@PathVariable int id, HttpSession httpSession) {
    logger.debug("edit()");
    Pet pet = petRepository.getPetById(id);
    ensureExist(pet);
    httpSession.setAttribute("id", pet.getId());
    return createFormModelView(pet, FormMode.Edit);
  }

  @GetMapping("new")
  public ModelAndView initPetCreationForm(HttpSession httpSession) {
    logger.debug("initPetCreationForm()");
    httpSession.setAttribute("id", -1);
    return createFormModelView(new Pet(), FormMode.Edit);
  }

  @PostMapping("new")
  public String processPetCreationForm(
      @Valid @ModelAttribute Pet pet,
      BindingResult bindingResult,
      Model model,
      @ModelAttribute("id") int petId,
      SessionStatus sessionStatus) {

    logger.debug("processPetCreationForm()");
    if (bindingResult.hasErrors()) {
      model.addAttribute("mode", FormMode.Edit);
      return "pet/viewOrEdit";
    }
    if (petId != -1) {
      pet.setId(petId);
      petRepository.updatePet(pet);
    } else {
      petId = petRepository.insertPet(pet);
    }
    sessionStatus.setComplete();
    return "redirect:/pets/" + petId;
  }

  @GetMapping("{id}/delete")
  public String delete(@PathVariable int id) {
    logger.debug("delete()");
    petRepository.delete(id);
    return "redirect:/pets";
  }

  private void ensureExist(Object object) {
    if (object == null)
      // TODO: set status code to 404.
      throw new RuntimeException();
  }

  private ModelAndView createFormModelView(Pet pet, FormMode mode) {
    return new ModelAndView("pet/viewOrEdit")
        .addObject("pet", pet)
        .addObject("mode", mode);
  }

}
