package tk.puncha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tk.puncha.dao.OwnerDAO;
import tk.puncha.models.Owner;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

  @Autowired
  private OwnerDAO ownerDAO;

  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public ModelAndView index() {
    List<Owner> owners = ownerDAO.getAllOwners();
    ModelAndView modelView = new ModelAndView();
    modelView.addObject("owners", owners);
    modelView.setViewName("owner/index");
    return modelView;
  }

  @RequestMapping(path = "new", method = RequestMethod.GET)
  public ModelAndView newOwnerForm() {
    throw new NotImplementedException();
  }

  @RequestMapping(path = "new", method = RequestMethod.POST)
  public ModelAndView createNewOwner() {
    // dispatch to ownerDAO to create a new owner
    throw new NotImplementedException();
  }

  @RequestMapping(path = "delete", method = RequestMethod.POST)
  public String deleteOwner(Owner owner) {
    int id = owner.getId();
    ownerDAO.deleteOwner(id);
    return "redirect:/owner/index";
  }
}
