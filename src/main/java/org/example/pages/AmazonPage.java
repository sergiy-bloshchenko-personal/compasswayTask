package org.example.pages;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class AmazonPage {
    public SelenideElement modal = $(By.className("glow-toaster-content"));
    public SelenideElement modalDismissBtn = modal.find(By.className("a-button-input"));
    public SelenideElement amazonLogo = $("#nav-logo");
    public SelenideElement language = $("#nav-tools .icp-nav-link-inner .nav-line-2");
    public SelenideElement deliveryLogo = $("#glow-ingress-block");
    public SelenideElement searchAmazon = $("#twotabsearchtextbox");
    public SelenideElement topMenu = $("#nav-xshop");
    public SelenideElement todayDealTab = topMenu.find(By.xpath(".//*[@data-csa-c-content-id='nav_cs_gb']"));
    public SelenideElement todayDealTitle = $(By.xpath("//*[@data-id='deals-header']"));
    public SelenideElement customerServiceTab = topMenu.find(By.xpath(".//*[@data-csa-c-content-id='nav_cs_customerservice']"));
    public SelenideElement customerServiceTitle = $(By.className("cs-title"));
    public SelenideElement registryTab = topMenu.find(By.xpath(".//*[@data-csa-c-content-id='nav_cs_registry']"));
    public SelenideElement registryTitle = $(By.xpath("//*[@aria-label='registry & gifting']"));
    public SelenideElement giftCardsTab = topMenu.find(By.xpath(".//*[@data-csa-c-content-id='nav_cs_gc']"));
    public SelenideElement giftCardsTitle = $(By.xpath("//*[@alt='Gift Cards']"));
    public SelenideElement sellTab = topMenu.find(By.xpath(".//*[@data-csa-c-content-id='nav_cs_sell']"));
    public SelenideElement sellTitle = $(By.xpath("//*[normalize-space(text())='Sell with Amazon']"));

    public void changeLanguage(String language) {
        $("#nav-flyout-icp .nav-template")
                .find(By.xpath(".//*[@lang='"+language+"']"))
                .should(Condition.visible, Duration.ofSeconds(3))
                .click();
    }
}
