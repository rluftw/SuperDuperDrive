package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(id = "signupButton")
    private WebElement signUpButton;

    @FindBy(id = "successMessage")
    private WebElement successBlock;

    @FindBy(id = "loginLink")
    private WebElement loginLink;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void register(String firstName, String lastName, String username, String password) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signUpButton.click();
    }
}
