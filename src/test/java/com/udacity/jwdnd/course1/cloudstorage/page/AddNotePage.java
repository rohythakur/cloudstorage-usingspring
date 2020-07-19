package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddNotePage {

    @FindBy(css = "#note-title")
    private WebElement noteTitle;

    @FindBy(css = "#note-description")
    private WebElement noteDescription;

    @FindBy(css = "#saveChangesButton")
    private WebElement saveChangesButton;

    public AddNotePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void saveNote(String title, String description) {
        noteTitle.clear();
        noteDescription.clear();
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        saveChangesButton.click();
    }
}
