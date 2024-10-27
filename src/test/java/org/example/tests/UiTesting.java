package org.example.tests;

import com.codeborne.selenide.AssertionMode;
import com.codeborne.selenide.Configuration;
import org.example.pages.AmazonPage;
import org.example.pages.CaptchaPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Story;
import ru.yandex.qatools.allure.annotations.TestCaseId;

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

        amazonPage.modal.shouldBe(visible);
        amazonPage.modalDismissBtn.click();
        amazonPage.modal.shouldNotBe(visible);
    }

    @After
    public void closeDriver(){
        closeWebDriver();
    }
    
    @Test
    @TestCaseId("UI.AmazonPage.1")
    @Story("User should be able to navigate to the main Amazon page with default language EN")
    public void mainPageShouldBeOpened(){
        amazonPage.amazonLogo.shouldBe(visible);
        amazonPage.language.shouldHave(text("EN"));
        amazonPage.deliveryLogo.shouldHave(text("Deliver to Ukraine"));
    }

    @Test
    @TestCaseId("UI.AmazonPage.2")
    @Story("User should be able to change language on the main Amazon page")
    public void languageCanBeChanged(){
        amazonPage.language.shouldBe(visible).hover();
        amazonPage.changeLanguage("de-DE");
        amazonPage.language.shouldHave(text("DE"));
        amazonPage.deliveryLogo.shouldHave(text("Liefern nach Ukraine"));
    }

    @Test
    @TestCaseId("UI.AmazonPage.3")
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
