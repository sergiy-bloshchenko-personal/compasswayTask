package org.example.tests;

import com.codeborne.selenide.AssertionMode;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit.ScreenShooter;
import io.qameta.allure.Attachment;
import org.example.pages.AmazonPage;
import org.example.pages.CaptchaPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.openqa.selenium.OutputType;

import java.time.Duration;
import java.util.Base64;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class UiTesting {
    public CaptchaPage captchaPage;
    public AmazonPage amazonPage;

    @Before
    public void init(){
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

        try {
            if (amazonPage.modal.should(Condition.appear,Duration.ofSeconds(3)).exists()) {
                amazonPage.modalDismissBtn.click();
                amazonPage.modal.shouldNotBe(visible);
            }
        } catch (Exception e){}
    }

    @After
    public void closeDriver(){
        closeWebDriver();
    }

//    @Rule
//    public ScreenShooter makeScreenshotOnFailure = ScreenShooter.failedTests();

//    @Attachment
//    public byte[] screenshot() {
//        return captureScreenShot();
//    }
//
//    private byte[] captureScreenShot() {
//        try {
//            return Base64.getDecoder().decode(Selenide.screenshot(OutputType.BYTES));
//        } catch (Exception e) {
//            return ("Can not parse screen shot data \n" + e).getBytes();
//        }
//    }

    @Test
    @TmsLink("UI.AmazonPage.1")
    @Story("User should be able to navigate to the main Amazon page with default language EN")
    public void mainPageShouldBeOpened(){
        amazonPage.amazonLogo.shouldBe(visible);
        amazonPage.language.shouldHave(text("EN"));
        amazonPage.deliveryLogo.shouldHave(text("Deliver to"));
    }

    @Test
    @TmsLink("UI.AmazonPage.2")
    @Story("User should be able to change language on the main Amazon page")
    public void languageCanBeChanged(){
        amazonPage.language.shouldBe(visible);
        amazonPage.changeLanguage("de-DE");
        amazonPage.language.shouldHave(text("DE"));
        amazonPage.deliveryLogo.shouldHave(text("Liefern nach"));
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
