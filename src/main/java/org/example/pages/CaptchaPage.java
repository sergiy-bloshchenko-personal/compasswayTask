package org.example.pages;
import com.codeborne.selenide.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CaptchaPage {
    public SelenideElement chaptchaInput = $("#captchacharacters");
    public SelenideElement onclick = $(By.xpath("//*[@onclick='window.location.reload()']"));
}
