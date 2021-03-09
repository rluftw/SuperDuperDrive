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
        new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
        new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
