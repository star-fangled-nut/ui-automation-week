package com.ministryoftesting.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPanel {
    protected WebDriver driver;

    private final By usernameField = By.cssSelector("[data-testid='username']");

    private final By passwordField = By.cssSelector("[data-testid='password']");
    private final By submitButton = By.cssSelector("[data-testid='submit']");

    public LoginPanel(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }
}
