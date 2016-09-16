package tk.puncha.viewResolvers;


import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

// Hack: I cannot make MyErrorViewResolver work when accessing an invalid path,
// so I have to test its method here... How can I really test it?
public class MyErrorViewResolverTests {
  private MyErrorViewResolver resolver;

  @Before
  public void initMyErrorViewResolver() {
    resolver = new MyErrorViewResolver();
  }

  @Test
  public void shouldRedirectTo404PageWhenStatusCodeIs404() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    HashMap<String, Object> model = new HashMap<>();
    ModelAndView modelAndView = resolver.resolveErrorView(request, HttpStatus.NOT_FOUND, model);
    assertEquals(modelAndView.getViewName(), "forward:/resources/error/404.html");
  }

  @Test
  public void shouldReturnNullWhenStatusCodeIsAllButNot404() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest();
    HashMap<String, Object> model = new HashMap<>();
    ModelAndView modelAndView = resolver.resolveErrorView(request, HttpStatus.SERVICE_UNAVAILABLE, model);
    assertNull(modelAndView);
  }
}
