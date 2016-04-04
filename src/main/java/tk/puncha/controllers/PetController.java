package tk.puncha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.models.Pet;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.repositories.PetRepository;
import tk.puncha.views.PetView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController extends ControllerBase {

  @Autowired
  private PetRepository petRepository;
  @Autowired
  private OwnerRepository ownerRepository;


  @RequestMapping(path = {"", "/index", "/default"}, method = RequestMethod.GET)
  public ModelAndView index() {
    List<PetView> petViews = petRepository.getAllPets();
    return new ModelAndView("pet/index", "pets", petViews);
  }

  @RequestMapping(path = "{id}", method = RequestMethod.GET)
  public ModelAndView view(@PathVariable int id) {
    Pet pet = petRepository.getPetById(id);
    ensureExist(pet);
    return createFormModelView(pet, FormMode.Readonly);
  }

  @RequestMapping(path = "{id}/edit", method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable int id) {
    Pet pet = petRepository.getPetById(id);
    ensureExist(pet);
    return createFormModelView(pet, FormMode.Edit);
  }


  @RequestMapping(path = "new", method = RequestMethod.GET)
  public ModelAndView createPetForm() {
    return createFormModelView(new Pet(), FormMode.Edit);
  }


  @RequestMapping(path = "new", method = RequestMethod.POST)
  public String createOrEdit(
      @Valid @ModelAttribute Pet pet, BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("types", petRepository.getAllTypes());
      model.addAttribute("owners", ownerRepository.getAllOwners());
      model.addAttribute("mode", FormMode.Edit);
      return "pet/viewOrEdit";
    }
    if (pet.getId() != -1)
      petRepository.updatePet(pet);
    else
      petRepository.insertPet(pet);
    return "redirect:/pets/" + pet.getId();
  }

  @RequestMapping(path="{id}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable int id){
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
        .addObject("types", petRepository.getAllTypes())
        .addObject("owners", ownerRepository.getAllOwners())
        .addObject("pet", pet)
        .addObject("mode", mode);
  }
}
