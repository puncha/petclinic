package tk.puncha.viewResolvers;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractView;
import tk.puncha.views.OwnerExcelView;

import java.util.Locale;

public class ExcelViewResolver implements ViewResolver {

  @Override
  public View resolveViewName(String viewName, Locale locale) throws Exception {
    switch (viewName) {
      case "owner/index":
        AbstractView view = new OwnerExcelView();
        view.setBeanName(viewName);
        return view;
    }

    return null;
  }
}