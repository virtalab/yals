package eu.yals.test.ui.vaadin;

import eu.yals.test.ui.vaadin.pageobjects.HomeViewPageObject;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.fail;

public abstract class HomePageTest extends VaadinTest {
  protected HomeViewPageObject homeView;

  public void openHomePage() {
    open("/");
    homeView = HomeViewPageObject.getPageObject(getDriver());
  }

  public void assertThatErrorNotificationIsNotVisible() {
    try {
      homeView.getErrorNotification();
      fail("Error Notification shown");
    } catch (NoSuchElementException e) {
      // okay to skip due to Vaadin TestBench logic
    }
  }
}