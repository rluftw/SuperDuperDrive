package com.udacity.jwdnd.course1.cloudstorage.page;


import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {
    @FindBy(xpath = "//a[@id='nav-notes-tab']")
    private WebElement notesTab;

    @FindBy(id = "add-note-modal-button")
    private WebElement addNoteModalButton;

    @FindBy(id = "note-title-row")
    private WebElement noteTitle;

    @FindBy(id = "note-description-row")
    private WebElement noteDescription;

    @FindBy(id = "save-changes-button")
    private WebElement saveChangesButton;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "edit-note-button")
    private WebElement editFirstNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteFirstNoteButton;

    @FindBy(id = "userTable")
    private WebElement notesTable;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void addNewNote(String title, String description) {
        goToNotesTab();
        waitForVisibility(addNoteModalButton);
        addNoteModalButton.click();
        saveNote(title, description);
    }

    public void goToNotesTab() {
        waitForVisibility(notesTab);
        notesTab.click();
    }

    public Note getFirstNote() {
        goToNotesTab();
        waitForVisibility(noteTitle);
        return new Note(null, noteTitle.getText(), noteDescription.getText(), null);
    }

    public void editNote(String title, String description) {
        goToNotesTab();
        waitForVisibility(editFirstNoteButton);
        editFirstNoteButton.click();
        saveNote(title, description);
    }

    public void deleteNote() {
        goToNotesTab();
        waitForVisibility(deleteFirstNoteButton);
        deleteFirstNoteButton.click();
    }

    public void logout() {
        logoutButton.click();
    }

    public Boolean isNoteListEmpty() {
        waitForVisibility(notesTable);
        return driver.findElements(By.id("note-row")).isEmpty();
    }

    private void saveNote(String title, String description) {
        waitForVisibility(noteTitleField);
        noteTitleField.clear();
        noteDescriptionField.clear();
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        waitForVisibility(saveChangesButton);
        saveChangesButton.click();
    }

    private void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }
}
