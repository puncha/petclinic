package tk.puncha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/owners")
public class OwnerController {
  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public String index() {
    return "owner/index";
  }
}
