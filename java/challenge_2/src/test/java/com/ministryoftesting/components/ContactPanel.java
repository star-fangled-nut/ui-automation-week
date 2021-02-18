package com.ministryoftesting.components;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.Callable;

public class ContactPanel {

    protected WebDriver driver;

    private final By contactPanel = By.className("col-sm-5");

    private final By nameInputField = By.id("name");
    private final By emailInputField = By.id("email");
    private final By phoneNumberInputField = By.id("phone");

    private final By subjectInputField = By.id("subject");
    private final By messageInputField = By.id("description");

    private final By submitMessageButton = By.id("submitContact");

    public ContactPanel(WebDriver driver) {
        this.driver = driver;
    }

    public void enterContactDetails(String name, String email, String phoneNumber) {
        driver.findElement(nameInputField).sendKeys(name);
        driver.findElement(emailInputField).sendKeys(email);
        driver.findElement(phoneNumberInputField).sendKeys(phoneNumber);
    }

    public void enterMessageDetails(String subject, String message) {
        driver.findElement(subjectInputField).sendKeys(subject);
        driver.findElement(messageInputField).sendKeys(message);
    }

    public void submitMessage() {
        driver.findElement(submitMessageButton).click();
    }

    public Callable<Boolean> messageIsSubmittedSuccessfully() {
        return () -> driver.findElement(contactPanel).getText().contains("Thanks for getting in touch");
    }

    public boolean isMessageSubmittedSuccessfully() {
        try {
            Awaitility.await()
                    .atMost(Duration.ofSeconds(10))
                    .until(messageIsSubmittedSuccessfully());
        } catch (ConditionTimeoutException e) {
            throw new RuntimeException("Failed to submit contact message");
        }
        return true;
    }
}
