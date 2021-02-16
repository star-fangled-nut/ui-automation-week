package com.ministryoftesting.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MessagePanel {

    protected WebDriver driver;

    private final By unreadMessages = By.className("read-false");

    public MessagePanel(WebDriver driver) {
        this.driver = driver;
    }

    public boolean unreadMessagesExist() {
        int unreadMessageCount = driver.findElements(unreadMessages).size();
        return unreadMessageCount >= 1;
    }
}
