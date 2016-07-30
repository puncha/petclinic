package tk.puncha.integration.controllers;

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

public class OwnerControllerTests extends ControllerTests {
  @Configuration
  @EnableAutoConfiguration
  static class Dummy extends Application {
  }

  @Before
  public void before() {
    createWebClient();
  }

  @Test
  public void shouldShowOwnerIndexPage() throws Exception {
    HtmlPage ownerIndexPage = getPage("/owners");
    assertEquals(10.0, ownerIndexPage.getByXPath("count(//tbody/tr)").get(0));

    List ownerProperties = ownerIndexPage.getByXPath("//tbody/tr[1]/td/text()");
    assertEquals("George", ownerProperties.get(0).toString());
    assertEquals("Franklin", ownerProperties.get(1).toString());
    assertEquals("110 W. Liberty St.", ownerProperties.get(2).toString());
    assertEquals("Madison", ownerProperties.get(3).toString());
    assertEquals("6085551023", ownerProperties.get(4).toString());
  }

  @Test
  public void shouldOwnerIndexPageNavigateToOwnerDetailPage() throws Exception {
    HtmlPage ownerIndexPage = getPage("/owners");
    List viewEditDeleteButtons = ownerIndexPage.getByXPath("//tbody/tr[1]/td[6]//a");
    HtmlAnchor aHref = (HtmlAnchor) viewEditDeleteButtons.get(0);
    HtmlPage viewOwnerPage = aHref.click();
    assertTrue(viewOwnerPage.getUrl().toString().matches(".*/owners/1$"));
  }

  @Test
  public void shouldShowOwnerDetailPage() throws Exception {
    HtmlPage page = getPage("/owners/1");
    String content = page.asText();

    // Owner
    assertTrue(content.contains("George"));
    assertTrue(content.contains("Franklin"));
    assertTrue(content.contains("110 W. Liberty St."));
    assertTrue(content.contains("Madison"));
    assertTrue(content.contains("6085551023"));

    // Pet
    assertTrue(content.contains("Leo"));
  }

  // TODO: Implement this test
  @Test
  public void shouldEditOwner() throws Exception {
  }

//  @Test
//  public void shouldDeleteAllOwners() throws Exception {
//    HtmlPage page = getPage("/owners");
//    for (int i = 0; i < 10; ++i) {
//      HtmlAnchor deleteOwnerHref = (HtmlAnchor) page.getByXPath("//tbody/tr[1]/td[6]//a[3]").get(0);
//      assertNotNull(deleteOwnerHref);
//      page = deleteOwnerHref.click();
//    }
//    TestCase.assertEquals(0, page.getByXPath("//tbody/tr").size());
//  }
}
