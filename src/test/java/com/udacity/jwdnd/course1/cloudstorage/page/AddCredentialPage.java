package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCredentialPage {

    @FindBy(css = "#credential-url")
    private WebElement urlText;

    @FindBy(css = "#credential-username")
    private WebElement usernameText;

    @FindBy(css = "#credential-password")
    private WebElement passwordText;

    @FindBy(css = "#saveCredentialButton")
    private WebElement saveCredentialButton;

    public AddCredentialPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void saveCredential(String url, String username, String password) {
        urlText.clear();
        usernameText.clear();
        passwordText.clear();
        urlText.sendKeys(url);
        usernameText.sendKeys(username);
        passwordText.sendKeys(password);
        saveCredentialButton.click();
    }
}
