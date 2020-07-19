package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SuccessPage {

    @FindBy(css = "#homeLink")
    private WebElement homeLink;

    public SuccessPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void goToHomePage() {
        homeLink.click();
    }
}
