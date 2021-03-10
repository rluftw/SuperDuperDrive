package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(name = "username")
    private WebElement usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(String username, String password) {
        waitForVisibility(usernameField);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        waitForVisibility(loginButton);
        loginButton.click();
    }

    private void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
    }
}
