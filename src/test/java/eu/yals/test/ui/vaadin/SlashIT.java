package eu.yals.test.ui.vaadin;

import com.codeborne.selenide.SelenideElement;
import com.vaadin.flow.component.html.testbench.H1Element;
import eu.yals.test.ui.vaadin.commons.SlashCommons;
import eu.yals.test.ui.vaadin.pageobjects.HomeViewElement;
import eu.yals.test.ui.vaadin.pageobjects.external.VR;
import eu.yals.test.ui.vaadin.tech.ElementConverter;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.open;


public class SlashIT extends SlashCommons {
    @Override
    protected HomeViewElement openView() {
        return $(HomeViewElement.class).waitForFirst();
    }

    @Test
    public void saveLinkAndClickOnResult() {
        HomeViewElement homeView = openView();
        pasteValueInFormAndSubmitIt("https://vr.fi");

        homeView.getShortLinkField().click();

        verifyThatVROpened();
    }

    @Test
    public void saveLinkAndCopyValueAndOpenIt() {
        HomeViewElement homeView = openView();
        pasteValueInFormAndSubmitIt("https://vr.fi");

        String shortUrl = homeView.getShortLinkField().getText();
        Assert.assertTrue(StringUtils.isNotBlank(shortUrl));

        open(shortUrl);
        verifyThatVROpened();
    }

    @Test
    public void openSomethingNonExisting() {
        open("/perkele");
        verifyThatPage404Opened();
    }

    @Test
    public void openSomethingNonExistingDeeperThanSingleLevel() {
        open("/void/something/here");
        verifyThatPage404Opened();
    }

    private void verifyThatVROpened() {
        WebElement logo = findElement(VR.LOGO);
        String logoAttribute = logo.getAttribute("alt");
        Assert.assertEquals("VR", logoAttribute);
    }

    private void verifyThatPage404Opened() {
       /* NotFoundViewElement page404 = $(NotFoundViewElement.class).onPage().waitForFirst();
        page404.TITLE.shouldBe(exist);
        Assert.assertTrue(page404.getTitle().getText().contains("404"));*/

        H1Element title = $(H1Element.class).first();
        SelenideElement titleSE = ElementConverter.get().convert(title);

        titleSE.should(exist);
        Assert.assertTrue(title.getText().contains("404"));
    }
}