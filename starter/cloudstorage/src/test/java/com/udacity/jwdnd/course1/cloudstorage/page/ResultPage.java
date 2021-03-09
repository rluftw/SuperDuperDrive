package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {
    @FindBy(id = "success")
    private WebElement successBlock;

    @FindBy(id = "failure")
    private WebElement failureBlock;

    @FindBy(id = "error")
    private WebElement errorBlock;

    @FindBy(id = "success-continue")
    private WebElement successContinueLink;

    private WebDriver driver;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String getSuccessMessage() {
        return successBlock.getText();
    }

    public void clickOnSuccessContinue() throws InterruptedException {
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(successContinueLink)).click();
        Thread.sleep(5000);
    }
}
