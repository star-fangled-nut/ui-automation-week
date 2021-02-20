package com.ministryoftesting.components;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.Callable;

public class BrandingPanel {

    protected WebDriver driver;

    private final By nameField = By.id("name");

    private final By submitButton = By.id("updateBranding");

    private final By brandingUpdatedModal = By.className("ReactModal__Content");

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(submitButton).click();
    }

    public BrandingPanel(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isBrandingUpdatedModalDisplayed() {
        try {
            Awaitility.await()
                    .atMost(Duration.ofSeconds(10))
                    .until(closeModalButtonIsDisplayed());
        } catch (ConditionTimeoutException exception) {
            return false;
        }
        return true;
    }

    private Callable<Boolean> closeModalButtonIsDisplayed() {
        return () -> driver.findElement(brandingUpdatedModal).isDisplayed();
    }
}
