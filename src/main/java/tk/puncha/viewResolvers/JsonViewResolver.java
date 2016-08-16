package tk.puncha.viewResolvers;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

public class JsonViewResolver implements ViewResolver {
  @Override
  public View resolveViewName(String viewName, Locale locale) throws Exception {
    MappingJackson2JsonView view = new MappingJackson2JsonView();
    view.setExtractValueFromSingleKeyModel(true);
    view.setPrettyPrint(true);
    return view;
  }
}
