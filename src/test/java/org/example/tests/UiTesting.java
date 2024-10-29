package org.example.tests;

import com.codeborne.selenide.AssertionMode;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.example.pages.AmazonPage;
import org.example.pages.CaptchaPage;
import org.junit.Rule;
import org.junit.Test;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import java.util.Base64;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class UiTesting {
    public CaptchaPage captchaPage;
    public AmazonPage amazonPage;

    @Rule
    public TestRule screenShotRule = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            System.out.println("Starting " + description.getMethodName() + " test");
            //commit this if running on windows
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
            Configuration.browser = "chrome";
            Configuration.assertionMode = AssertionMode.STRICT;
            Configuration.browserSize = "1920x1080";
            Configuration.headless = false;

            open("https://www.amazon.com/");
            captchaPage = new CaptchaPage();
            amazonPage = new AmazonPage();

            if (captchaPage.chaptchaInput.exists()) {
                captchaPage.chaptchaInput.shouldBe(visible);
                if (captchaPage.onclick.exists()) captchaPage.onclick.click();
            }

            amazonPage.modal.shouldBe(visible);
            amazonPage.modalDismissBtn.click();
            amazonPage.modal.shouldNotBe(visible);
        }

        @Override
        protected void finished(Description description) {
        }

        @Override
        protected void succeeded(Description description) {
            System.out.println(description.getMethodName() + " has passed.");
            closeWebDriver();
        }

        @Override
        protected void failed(Throwable e, Description description) {
            captureScreenShot();
            System.out.println(description.getMethodName() + " has failed.");
            closeWebDriver();
        }
    };

    private byte[] captureScreenShot() {
        try {
            return Base64.getDecoder().decode(Selenide.screenshot(OutputType.BASE64));
        } catch (Exception e) {
            return ("Can not parse screen shot data \n" + e).getBytes();
        }
    }
    
    @Test
    @TmsLink("UI.AmazonPage.1")
    @Story("User should be able to navigate to the main Amazon page with default language EN")
    public void mainPageShouldBeOpened(){
        amazonPage.amazonLogo.shouldBe(visible);
        amazonPage.language.shouldHave(text("EN"));
        amazonPage.deliveryLogo.shouldHave(text("Deliver to Ukraine"));
    }

    @Test
    @TmsLink("UI.AmazonPage.2")
    @Story("User should be able to change language on the main Amazon page")
    public void languageCanBeChanged(){
        amazonPage.language.shouldBe(visible).hover();
        amazonPage.changeLanguage("de-DE");
        amazonPage.language.shouldHave(text("DE"));
        amazonPage.deliveryLogo.shouldHave(text("Liefern nach Ukraine"));
    }

    @Test
    @TmsLink("UI.AmazonPage.3")
    @Story("User should be able to navigate through Top menu")
    public void navigationThroughTopMenu(){
        amazonPage.todayDealTab.shouldBe(visible).click();
        amazonPage.todayDealTitle.shouldBe(visible);

        amazonPage.customerServiceTab.shouldBe(visible).click();
        amazonPage.customerServiceTitle.shouldBe(visible);

        amazonPage.registryTab.shouldBe(visible).click();
        amazonPage.registryTitle.shouldBe(visible);

        amazonPage.giftCardsTab.shouldBe(visible).click();
        amazonPage.giftCardsTitle.shouldBe(visible);

        amazonPage.sellTab.shouldBe(visible).click();
        amazonPage.sellTitle.shouldBe(visible);

        amazonPage.amazonLogo.shouldBe(visible).click();
        amazonPage.sellTitle.shouldNotBe(visible);
    }

}
