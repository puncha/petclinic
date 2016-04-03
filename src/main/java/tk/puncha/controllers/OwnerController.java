package tk.puncha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.dao.PetDAO;
import tk.puncha.models.Owner;
import tk.puncha.validators.OwnerValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

  enum OwnerFormMode {
    Readonly,
    Edit
  }

  @Autowired
  private OwnerDAO ownerDAO;

  @Autowired
  private PetDAO petDAO;

  @Autowired
  private OwnerValidator ownerValidator;

  @InitBinder
  public void dataBinding(WebDataBinder binder) {
    binder.addValidators(ownerValidator);
  }

  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public ModelAndView index() {
    List<Owner> owners = ownerDAO.getAllOwners();
    ModelAndView modelView = new ModelAndView();
    modelView.addObject("owners", owners);
    modelView.setViewName("owner/index");
    return modelView;
  }

  @RequestMapping(path = {"{ownerId}"}, method = RequestMethod.GET)
  public ModelAndView viewOwner(@PathVariable int ownerId) {
    Owner owner = ownerDAO.getOwnerById(ownerId);
    EnsureOwnerExists(owner);
    return createOwnerFormModelView("owner/viewOrEdit", owner, OwnerFormMode.Readonly);
  }

  @RequestMapping(path = "new", method = RequestMethod.GET)
  public ModelAndView createOwner() {
    return createOwnerFormModelView("owner/viewOrEdit", new Owner(), OwnerFormMode.Edit);
  }

  @RequestMapping(path = "new", method = RequestMethod.POST)
  public String createOrUpdateOwner(@Valid Owner owner, BindingResult bindingResult) {
    if (bindingResult.hasErrors())
      return "owner/viewOrEdit";

    if (owner.getId() == -1)
      ownerDAO.insertOwner(owner);
    else
      ownerDAO.updateOwner(owner);
    return "redirect:/owners/" + owner.getId();
  }

  @RequestMapping(path = "{ownerId}/edit", method = RequestMethod.GET)
  public ModelAndView editOwner(@PathVariable int ownerId) {
    Owner owner = ownerDAO.getOwnerById(ownerId);
    EnsureOwnerExists(owner);
    return createOwnerFormModelView("owner/viewOrEdit", owner, OwnerFormMode.Edit);
  }

  private void EnsureOwnerExists(Owner owner) {
    if (owner == null)
      // TODO: set status code to 404.
      throw new RuntimeException();
  }

  @RequestMapping(path = "{ownerId}/delete", method = RequestMethod.GET)
  public String deleteOwner(@PathVariable int ownerId) {
    petDAO.deletePetsByOwnerId(ownerId);
    ownerDAO.deleteOwner(ownerId);
    return "redirect:/owners";
  }

  public ModelAndView createOwnerFormModelView(String viewName, Owner owner, OwnerFormMode mode) {
    return new ModelAndView(viewName).addObject("owner", owner).addObject("mode", mode);
  }

}

