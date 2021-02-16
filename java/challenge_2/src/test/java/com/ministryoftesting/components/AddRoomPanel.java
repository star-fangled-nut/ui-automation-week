package com.ministryoftesting.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddRoomPanel {

    protected WebDriver driver;

    private final By roomNumberInputField = By.id("roomNumber");

    private final By roomPriceInputField = By.id("roomPrice");
    private final By createRoomButton = By.id("createRoom");

    private final By savedRoomDetails = By.className("row detail");

    public AddRoomPanel(WebDriver driver) {
        this.driver = driver;
    }

    public void addRoom(String roomNumber, String price) {
        driver.findElement(roomNumberInputField).sendKeys(roomNumber);
        driver.findElement(roomPriceInputField).sendKeys(price);
        driver.findElement(createRoomButton).click();
    }

    public int getSavedRoomCount() {
        return driver.findElements(savedRoomDetails).size();
    }
}
