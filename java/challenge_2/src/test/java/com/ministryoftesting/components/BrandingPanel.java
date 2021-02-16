package com.ministryoftesting.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class BrandingPanel {

    protected WebDriver driver;

    private final By nameField = By.id("name");

    private final By submitButton = By.id("updateBranding");

    private final By closeModalButton = By.xpath("//button[text()=\"Close\"]");

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(submitButton).click();
    }

    public BrandingPanel(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isCloseModalButtonDisplayed() {
        try {
            return driver.findElement(closeModalButton).isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}
