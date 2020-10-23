package eu.yals.test.ui.appinfo;

import eu.yals.test.ui.SelenideTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static eu.yals.test.pageobjects.AppInfoPageObject.PublicInfoArea.*;

/**
 * Checking elements of public info area with information about version
 *
 * @since 2.7
 */
@SpringBootTest
public class PublicInfoTest extends SelenideTest {

    @Before
    public void beforeTest() {
        open("/appInfo");
        updateTestNameAndStartVideo();
    }

    @Test
    public void publicAreaIsVisible() {
        PUBLIC_INFO_AREA.should(exist);
        PUBLIC_INFO_AREA.shouldBe(visible);
    }

    @Test
    public void publicAreaHasAllRequiredElements() {
        VERSION.shouldBe(visible);
        VERSION.shouldHave(text("version"));
        VERSION.shouldHave(text("commit"));

        COMMIT_LINK.shouldBe(visible);
        COMMIT_LINK.shouldNotBe(empty);
        COMMIT_LINK.shouldHave(attribute("href"));

    }

}
