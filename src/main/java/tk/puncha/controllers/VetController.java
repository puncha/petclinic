package tk.puncha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/vets")
public class VetController {
  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public String index() {
    return "vet/index";
  }
}
