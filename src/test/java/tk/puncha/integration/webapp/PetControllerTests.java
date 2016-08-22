package tk.puncha.integration.webapp;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import tk.puncha.Application;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class PetControllerTests extends ControllerTests {

  @Configuration
  @EnableAutoConfiguration()
  static class Dummy extends Application {
  }

  @Before
  public void before() {
    createWebClient();
  }

  @Test
  public void shouldShowPetIndexPage() throws Exception {
    HtmlPage ownerIndexPage = getPage("/pets");
    assertEquals(13.0, ownerIndexPage.getByXPath("count(//div[@class='card'])").get(0));

    String content = ownerIndexPage.asText();
    assertTrue(content.contains("Leo"));
    assertTrue(content.contains("Owned by: George Franklin"));
    assertTrue(content.contains("Birthday: 2010-09-07"));
    assertTrue(content.contains("Type: cat"));


    List viewEditDeleteLinks = ownerIndexPage.getByXPath("//div[@class='card'][1]//@href");
    assertEquals("/owners/1", ((DomAttr) viewEditDeleteLinks.get(0)).getValue());
    assertEquals("/pets/1", ((DomAttr) viewEditDeleteLinks.get(1)).getValue());
    assertEquals("/pets/1/edit", ((DomAttr) viewEditDeleteLinks.get(2)).getValue());
    assertEquals("/pets/1/delete", ((DomAttr) viewEditDeleteLinks.get(3)).getValue());
  }

  @Test
  public void shouldShowPetDetailPage() throws Exception {
    HtmlPage page = getPage("/pets/7");
    String content = page.asText();

    // Pet
    assertTrue(content.contains("Samantha"));
    assertTrue(content.contains("cat"));
    assertTrue(content.contains("2012-09-04"));

    // Owner
    assertTrue(content.contains("Jean"));

    // Visits
    assertTrue(content.contains("2013-01-04 spayed"));
    assertTrue(content.contains("2013-01-01 rabies shot"));
  }

  // TODO: Implement this test
  @Test
  public void shouldEditPet() throws Exception {
  }
}
