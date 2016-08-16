package tk.puncha.integration.webapp;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTests {

  @Value("${local.server.port}")
  private String PORT;
  private static final String SERVER = "http://localhost";
  private WebClient webClient;

  void createWebClient() {
    webClient = new WebClient();
    webClient.getOptions().setJavaScriptEnabled(false);
    webClient.getOptions().setCssEnabled(false);
  }

  void setInput(HtmlPage page, String elementId, String value) {
    ((HtmlTextInput) page.getElementById(elementId)).setValueAttribute(value);
  }

  void setDateInput(HtmlPage page, String elementId, String value) {
    ((HtmlDateInput) page.getElementById(elementId)).setValueAttribute(value);
  }

  void selectItem(HtmlPage page, String elementId, String itemToSelect) {
    HtmlSelect select = (HtmlSelect) page.getElementById(elementId);
    HtmlOption option = select.getOptionByText(itemToSelect);
    select.setSelectedAttribute(option, true);
  }

  HtmlPage clickButton(HtmlPage page, String xpathExpr) throws java.io.IOException {
    return ((HtmlButton) page.getByXPath(xpathExpr).get(0)).click();
  }

  HtmlPage getPage(String path) throws java.io.IOException {
    return webClient.getPage(SERVER + ":" + PORT + path);
  }
}
