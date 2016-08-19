package tk.puncha.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.puncha.models.Pet;
import tk.puncha.models.Visit;
import tk.puncha.repositories.PetRepository;
import tk.puncha.repositories.VisitRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/pets/{petId}/visits")
public class VisitController {

  private final PetRepository petRepository;
  private final VisitRepository visitRepository;

  @Autowired
  public VisitController(PetRepository petRepository, VisitRepository visitRepository) {
    this.petRepository = petRepository;
    this.visitRepository = visitRepository;
  }

  @ModelAttribute("pet")
  Pet getPetById(@PathVariable int petId) {
    return petRepository.getById(petId);
  }

  @RequestMapping("{visitId}/delete")
  public String deleteVisit(@ModelAttribute Pet pet, @PathVariable int visitId) {
    if (pet == null) throw new RuntimeException("Pet doesn't exist!");
    visitRepository.deleteById(visitId);
    return "redirect:/pets/{petId}";
  }

  @GetMapping("/new")
  public ModelAndView initCreationForm() {
    return new ModelAndView("visit/new").addObject(new Visit());
  }

  @PostMapping("/new")
  public String processCreationForm(Pet pet, @ModelAttribute @Valid Visit visit, BindingResult bingResult) {
    if (bingResult.hasErrors()) {
      return "visit/new";
    }

    pet.getVisits().add(visit);
    visit.setPet(pet);
    petRepository.update(pet);
    return "redirect:/pets/{petId}";
  }
}
