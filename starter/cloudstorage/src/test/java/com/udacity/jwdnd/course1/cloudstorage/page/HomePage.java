package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(xpath = "//a[@id='nav-notes-tab']")
    private WebElement notesTab;

    @FindBy(xpath = "//a[@id='nav-credentials-tab']")
    private WebElement credentialsTab;

    @FindBy(id = "add-note-modal-button")
    private WebElement addNoteModalButton;

    @FindBy(id = "add-credential-modal-button")
    private WebElement addCredentialModalButton;

    @FindBy(id = "note-title-row")
    private WebElement noteTitle;

    @FindBy(id = "note-description-row")
    private WebElement noteDescription;

    @FindBy(id = "credential-url-row")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username-row")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password-row")
    private WebElement credentialPassword;

    @FindBy(id = "save-note-changes-button")
    private WebElement saveNoteChangesButton;

    @FindBy(id = "save-credential-changes-button")
    private WebElement saveCredentialChangesButton;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "edit-note-button")
    private WebElement editFirstNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteFirstNoteButton;

    @FindBy(id = "edit-credential-button")
    private WebElement editFirstCredentialButton;

    @FindBy(id = "delete-credential-button")
    private WebElement deleteFirstCredentialButton;

    @FindBy(id = "noteTable")
    private WebElement notesTable;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

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

    public void addNewCredential(String url, String username, String password) {
        goToCredentialsTab();
        waitForVisibility(addCredentialModalButton);
        addCredentialModalButton.click();
        saveCredential(url, username, password);
    }

    public void goToCredentialsTab() {
        waitForVisibility(credentialsTab);
        credentialsTab.click();
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

    public Credential getFirstCredential() {
        goToCredentialsTab();
        waitForVisibility(credentialUrl);
        return new Credential(
                null,
                credentialUrl.getText(),
                credentialUsername.getText(),
                null,
                credentialPassword.getText(),
                null
        );
    }

    public void editNote(String title, String description) {
        goToNotesTab();
        waitForVisibility(editFirstNoteButton);
        editFirstNoteButton.click();
        saveNote(title, description);
    }

    public void editCredential(String url, String username, String password) {
        goToCredentialsTab();
        waitForVisibility(editFirstCredentialButton);
        editFirstCredentialButton.click();
        saveCredential(url, username, password);
    }

    public void deleteNote() {
        goToNotesTab();
        waitForVisibility(deleteFirstNoteButton);
        deleteFirstNoteButton.click();
    }

    public void deleteCredential() {
        goToCredentialsTab();
        waitForVisibility(deleteFirstCredentialButton);
        deleteFirstCredentialButton.click();
    }

    public void logout() {
        logoutButton.click();
    }

    public Boolean isNoteListEmpty() {
        waitForVisibility(notesTable);
        return driver.findElements(By.id("note-row")).isEmpty();
    }

    public Boolean isCredentialListEmpty() {
        waitForVisibility(credentialTable);
        return driver.findElements(By.id("credential-row")).isEmpty();
    }

    private void saveNote(String title, String description) {
        waitForVisibility(noteTitleField);
        noteTitleField.clear();
        noteDescriptionField.clear();
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        waitForVisibility(saveNoteChangesButton);
        saveNoteChangesButton.click();
    }

    private void saveCredential(String url, String username, String password) {
        waitForVisibility(credentialUrlField);
        credentialUrlField.clear();
        credentialUsernameField.clear();
        credentialPasswordField.clear();

        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);

        waitForVisibility(saveCredentialChangesButton);
        saveCredentialChangesButton.click();
    }

    private void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }
}
