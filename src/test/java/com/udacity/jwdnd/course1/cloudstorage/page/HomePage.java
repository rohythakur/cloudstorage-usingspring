package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(css = "#logoutButton")
    private WebElement logout;

    @FindBy(css = "#addNoteButton")
    private WebElement addNoteButton;

    @FindBy(css = "#addCredentialButton")
    private WebElement addCredentialButton;

    @FindBy(css = "#editNoteButton")
    private WebElement editNoteButton;

    @FindBy(css = "#editCredentialButton")
    private WebElement editCredentialButton;

    @FindBy(css = "#deleteNoteButton")
    private WebElement deleteNoteButton;

    @FindBy(css = "#deleteCredentialButton")
    private WebElement deleteCredentialButton;

    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(className = "noteTitleText")
    private WebElement noteTitleText;

    @FindBy(className = "noteDescriptionText")
    private WebElement noteDescriptionText;

    @FindBy(className = "credentialUrlText")
    private WebElement credentialUrlText;

    @FindBy(className = "credentialUsernameText")
    private WebElement credentialUsernameText;

    @FindBy(className = "credentialPasswordText")
    private WebElement credentialPasswordText;

    private final WebDriver driver;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        logout.click();
    }

    public void goToNotesTab() {
        notesTab.click();
    }

    public void goToCredentialsTab() {
        credentialsTab.click();
    }

    public void openAddNoteDialog() {
        addNoteButton.click();
    }

    public void openAddCredentialDialog() {
        addCredentialButton.click();
    }

    public void editFirstNote() {
        editNoteButton.click();
    }

    public void editFirstCredential() {
        editCredentialButton.click();
    }

    public void deleteFirstNote() {
        deleteNoteButton.click();
    }

    public void deleteFirstCredential() {
        deleteCredentialButton.click();
    }

    public String getFirstNoteTitle() {
        return noteTitleText.getText();
    }

    public String getFirstNoteDescription() {
        return noteDescriptionText.getText();
    }

    public String getFirstCredentialUrl() {
        return credentialUrlText.getText();
    }

    public String getFirstCredentialUsername() {
        return credentialUsernameText.getText();
    }

    public String getFirstCredentialPassword() {
        return credentialPasswordText.getText();
    }

    public Boolean notesAreEmpty() {
        return driver.findElements(By.className("noteTitleText")).size() == 0;
    }

    public Boolean credentialsAreEmpty() {
        return driver.findElements(By.className("credentialUrlText")).size() == 0;
    }
}
