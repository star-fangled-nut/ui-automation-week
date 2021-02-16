package com.ministryoftesting.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class AdminNavBar {

    protected WebDriver driver;

    private final By navigationLink = By.className("nav-item");

    public AdminNavBar(WebDriver driver) {
        this.driver = driver;
    }

    public List<String> getNavigationLinkText() {
        return driver.findElements(navigationLink).stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
