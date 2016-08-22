package tk.puncha.viewResolvers;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyErrorViewResolver implements ErrorViewResolver {
  @Override
  public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
    if (status == HttpStatus.NOT_FOUND)
      return new ModelAndView("forward:/resources/error/404.html");
    return null;
  }
}
