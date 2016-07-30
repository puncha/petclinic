package tk.puncha.integration.controllers;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import tk.puncha.Application;

import static org.junit.Assert.assertTrue;

public class PetAndVisitCreationTests extends ControllerTests {

  @Configuration
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
    setInput(page, "birthDate", "1999-09-09");
    selectItem(page, "type", "lizard");
    selectItem(page, "owner", "Peter");
    HtmlPage petDetailPage = clickButton(page, "//button[@type='submit']");
    assertTrue(petDetailPage.getUrl().toString().matches(".*/pets/\\d+$"));
  }


  @Test
  public void shouldCreateVisit() throws Exception {
    HtmlPage page = getPage("/pets/1/visits/new");
    setInput(page, "description", "Hello, world!");
    setInput(page, "visitDate", "2000-02-02");
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
