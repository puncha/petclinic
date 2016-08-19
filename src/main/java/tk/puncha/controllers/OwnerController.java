package tk.puncha.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.models.Owner;
import tk.puncha.repositories.OwnerRepository;
import tk.puncha.validators.OwnerValidator;
import tk.puncha.views.json.view.OwnerJsonView;

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

  @InitBinder("owner")
  public void dataBinding(WebDataBinder binder) {
    binder.addValidators(ownerValidator);
  }

  // Multiple Views supported! Html, XML, Json
  @GetMapping({"", "index"})
  public ModelAndView index(@RequestParam(name = "search-first-name", required = false) String firstNameToSearch) {
    List<Owner> owners;
    if (firstNameToSearch == null || firstNameToSearch.isEmpty())
      owners = ownerRepository.getAll();
    else
      owners = ownerRepository.findByFirstName(firstNameToSearch);

    ModelAndView modelView = new ModelAndView();
    modelView.addObject("owners", owners);
    modelView.addObject(JsonView.class.getName(), OwnerJsonView.WithPets.class);
    modelView.setViewName("owner/index");
    return modelView;
  }

  @GetMapping("{ownerId}")
  public ModelAndView view(@PathVariable int ownerId) {
    Owner owner = ownerRepository.getByIdWithPets(ownerId);
    ensureExist(owner);
    return createFormModelView("owner/viewOrEdit", owner, FormMode.Readonly);
  }

  @GetMapping("new")
  public ModelAndView create() {
    return createFormModelView("owner/viewOrEdit", new Owner(), FormMode.Edit);
  }

  @PostMapping("new")
  public String createOrUpdate(@Valid Owner owner, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("mode", FormMode.Edit);
      return "owner/viewOrEdit";
    }

    if (owner.getId() == -1)
      ownerRepository.insert(owner);
    else
      ownerRepository.update(owner);
    return "redirect:/owners/" + owner.getId();
  }

  @GetMapping("{ownerId}/edit")
  public ModelAndView editOwner(@PathVariable int ownerId) {
    Owner owner = ownerRepository.getById(ownerId);
    ensureExist(owner);
    return createFormModelView("owner/viewOrEdit", owner, FormMode.Edit);
  }

  @GetMapping("{ownerId}/delete")
  public String delete(@PathVariable int ownerId) {
    ownerRepository.deleteById(ownerId);
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

