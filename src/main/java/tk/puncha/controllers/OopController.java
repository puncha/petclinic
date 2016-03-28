package tk.puncha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/oops")
public class OopController {
  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public String index() {
    throw new RuntimeException();
  }
}
