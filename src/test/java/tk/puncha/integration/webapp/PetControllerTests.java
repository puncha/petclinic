package tk.puncha.integration.webapp;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import tk.puncha.Application;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    assertEquals(13.0, ownerIndexPage.getByXPath("count(//tbody/tr)").get(0));

    List ownerProperties = ownerIndexPage.getByXPath("//tbody/tr[1]/td/text()");
    assertEquals("Leo", ownerProperties.get(0).toString());
    assertEquals("cat", ownerProperties.get(1).toString());
    assertEquals("2010-09-07", ownerProperties.get(2).toString());
    assertEquals("George Franklin", ownerProperties.get(3).toString());

    List viewEditDeleteLinks = ownerIndexPage.getByXPath("//tbody/tr[1]/td[5]//a");
    assertEquals("/pets/1", ((HtmlAnchor) viewEditDeleteLinks.get(0)).getHrefAttribute());
    assertEquals("/pets/1/edit", ((HtmlAnchor) viewEditDeleteLinks.get(1)).getHrefAttribute());
    assertEquals("/pets/1/delete", ((HtmlAnchor) viewEditDeleteLinks.get(2)).getHrefAttribute());
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
