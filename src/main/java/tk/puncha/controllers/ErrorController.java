package tk.puncha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.puncha.exceptions.OopsException;

@Controller
@RequestMapping("/errors")
public class ErrorController {

  public static final String DEFAULT_EXCEPTION = "Expected: controller used to showcase what happens when an exception is thrown";

  @GetMapping({"", "index", "default"})
  public String index() {
    final String msg = DEFAULT_EXCEPTION;
    throw new RuntimeException(msg);
  }

  @GetMapping("/oops")
  public String oops() {
    throw new OopsException();
  }

}
