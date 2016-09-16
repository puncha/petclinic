package tk.puncha.integration.webapp;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import tk.puncha.Application;

import static org.junit.Assert.assertTrue;

@Ignore
public class PetAndVisitCreationTests extends ControllerTests {

  @Configuration
  @EnableAutoConfiguration()
  static class Dummy extends Application {
  }

  @Before
  public void before() {
    createWebClient();
  }

  @Test
  public void shouldCreatePet() throws Exception {
    HtmlPage page = getPage("/pets/new");
    setInput(page, "name", "kasper");
    setDateInput(page, "birthDate", "1999-09-09");
    selectItem(page, "type", "lizard");
    selectItem(page, "owner", "Peter");
    HtmlPage petDetailPage = clickButton(page, "//button[@type='submit']");
    assertTrue(petDetailPage.getUrl().toString().matches(".*/pets/\\d+$"));
  }


  @Test
  public void shouldCreateVisit() throws Exception {
    HtmlPage page = getPage("/pets/1/visits/new");
    setInput(page, "description", "Hello, world!");
    setDateInput(page, "visitDate", "2000-02-02");
    HtmlPage petDetailPage = clickButton(page, "//button[@type='submit']");
    assertTrue(petDetailPage.getUrl().toString().matches(".*/pets/1$"));
    String content =petDetailPage.asText();
    assertTrue(content.contains("Hello, world!"));
    assertTrue(content.contains("2000-02-02"));
  }

  // TODO: Implement this test
  @Test
  public void shouldDeleteVisit() throws Exception {
  }
}
