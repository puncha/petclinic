package tk.puncha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tk.puncha.exceptions.OopsException;

@Controller
@RequestMapping("/errors")
public class ErrorController {

  public static final String DEFAULT_EXCEPTION = "Expected: controller used to showcase what happens when an exception is thrown";

  @RequestMapping(path = {"", "index", "default"}, method = RequestMethod.GET)
  public String index() {
    final String msg = DEFAULT_EXCEPTION;
    throw new RuntimeException(msg);
  }

  @RequestMapping(path = "/oops", method = RequestMethod.GET)
  public String oops() {
    throw new OopsException();
  }

}
