package tk.puncha.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.formatters.OwnerFormatter;
import tk.puncha.formatters.PetTypeFormatter;
import tk.puncha.models.Owner;
import tk.puncha.models.Pet;
import tk.puncha.models.PetType;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.repositories.PetRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pets")
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

  @InitBinder
  void initBinder(WebDataBinder binder) {
    binder.addCustomFormatter(ownerFormatter);
    binder.addCustomFormatter(petTypeFormatter);
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

  @RequestMapping(path = {"", "/index", "/default"}, method = RequestMethod.GET)
  public ModelAndView index() {
    logger.debug("index()");
    List<Pet> petViews = petRepository.getAllPets();
    return new ModelAndView("pet/index", "pets", petViews);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ModelAndView view(@PathVariable int id) {
    logger.debug("view()");
    Pet pet = petRepository.getPetById(id);
    ensureExist(pet);
    return createFormModelView(pet, FormMode.Readonly);
  }

  @RequestMapping(path = "{id}/edit", method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable int id) {
    logger.debug("edit()");
    Pet pet = petRepository.getPetById(id);
    ensureExist(pet);
    return createFormModelView(pet, FormMode.Edit);
  }

  @RequestMapping(path = "new", method = RequestMethod.GET)
  public ModelAndView initPetCreationForm() {
    logger.debug("initPetCreationForm()");
    return createFormModelView(new Pet(), FormMode.Edit);
  }

  @RequestMapping(path = "new", method = RequestMethod.POST)
  public String processPetCreationForm(
      @Valid @ModelAttribute Pet pet,
      BindingResult bindingResult, Model model) {

    logger.debug("processPetCreationForm()");
    if (bindingResult.hasErrors()) {
      model.addAttribute("mode", FormMode.Edit);
      return "pet/viewOrEdit";
    }
    int petId = pet.getId();
    if (petId != -1)
      petRepository.updatePet(pet);
    else
      petId = petRepository.insertPet(pet);
    return "redirect:/pets/" + petId;
  }

  @RequestMapping(path = "{id}/delete", method = RequestMethod.GET)
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
