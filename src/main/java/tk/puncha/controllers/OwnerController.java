package tk.puncha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.validators.OwnerValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController extends ControllerBase {

  private final OwnerRepository ownerRepository;
  private final OwnerValidator ownerValidator;

  @Autowired
  public OwnerController(OwnerRepository ownerRepository, OwnerValidator ownerValidator) {
    this.ownerRepository = ownerRepository;
    this.ownerValidator = ownerValidator;
  }

  @InitBinder
  public void dataBinding(WebDataBinder binder) {
    binder.addValidators(ownerValidator);
  }
  
  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public ModelAndView index() {
    List<Owner> owners = ownerRepository.getAllOwners();

    ModelAndView modelView = new ModelAndView();
    modelView.addObject("owners", owners);
    modelView.setViewName("owner/index");
    return modelView;
  }

  @RequestMapping(path = {"{ownerId}"}, method = RequestMethod.GET)
  public ModelAndView view(@PathVariable int ownerId) {
    Owner owner = ownerRepository.getOwnerWithPetsById(ownerId);
    ensureExist(owner);
    return createFormModelView("owner/viewOrEdit", owner, FormMode.Readonly);
  }

  @RequestMapping(path = "new", method = RequestMethod.GET)
  public ModelAndView create() {
    return createFormModelView("owner/viewOrEdit", new Owner(), FormMode.Edit);
  }

  @RequestMapping(path = "new", method = RequestMethod.POST)
  public String createOrUpdate(@Valid Owner owner, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("mode", FormMode.Edit);
      return "owner/viewOrEdit";
    }

    if (owner.getId() == -1)
      ownerRepository.insertOwner(owner);
    else
      ownerRepository.updateOwner(owner);
    return "redirect:/owners/" + owner.getId();
  }

  @RequestMapping(path = "{ownerId}/edit", method = RequestMethod.GET)
  public ModelAndView editOwner(@PathVariable int ownerId) {
    Owner owner = ownerRepository.getOwnerById(ownerId);
    ensureExist(owner);
    return createFormModelView("owner/viewOrEdit", owner, FormMode.Edit);
  }

  @RequestMapping(path = "{ownerId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable int ownerId) {
    ownerRepository.deleteOwner(ownerId);
    return "redirect:/owners";
  }

  private void ensureExist(Owner owner) {
    if (owner == null)
      // TODO: set status code to 404.
      throw new RuntimeException();
  }

  private ModelAndView createFormModelView(String viewName, Owner owner, FormMode mode) {
    return new ModelAndView(viewName).addObject("owner", owner).addObject("mode", mode);
  }

}

