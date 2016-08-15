package tk.puncha.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import tk.puncha.controllers.ErrorController;
import tk.puncha.exceptions.OopsException;

import static org.hamcrest.object.HasToString.hasToString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource(locations = {"/test.properties", "/unit-test.properties"})
@RunWith(SpringRunner.class)
@WebMvcTest(ErrorController.class)
public class ErrorControllerTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  public void shouldReturnIndexPage() throws Exception {
    String exceptionMsg = new RuntimeException(ErrorController.DEFAULT_EXCEPTION).toString();

    mockMvc.perform(get("/errors"))
        .andExpect(status().is(200))
        .andExpect(view().name("exception/default"))
        .andExpect(model().attribute("exception", hasToString(exceptionMsg)));
  }

  @Test
  public void shouldReturnOopsPage() throws Exception {
    String exceptionMsg = new OopsException().toString();
    mockMvc.perform(get("/errors/oops"))
        .andExpect(status().isOk())
        .andExpect(view().name("exception/oops"))
        .andExpect(model().attribute("exception", hasToString(exceptionMsg)));
  }
}
