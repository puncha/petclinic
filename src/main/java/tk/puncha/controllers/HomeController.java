package tk.puncha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

  @RequestMapping(path = {"", "index"}, method = RequestMethod.GET)
  public String index() {
    return "home/index";
  }
}
