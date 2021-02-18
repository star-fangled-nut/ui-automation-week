package com.ministryoftesting.components;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.Callable;

public class AddRoomPanel {

    protected WebDriver driver;

    private final By roomNumberInputField = By.id("roomNumber");

    private final By roomPriceInputField = By.id("roomPrice");
    private final By createRoomButton = By.id("createRoom");

    private final By savedRoomDetails = By.cssSelector("[data-type='room']");

    public AddRoomPanel(WebDriver driver) {
        this.driver = driver;
    }

    public void addRoom(String roomNumber, String price) {
        driver.findElement(roomNumberInputField).sendKeys(roomNumber);
        driver.findElement(roomPriceInputField).sendKeys(price);
    }

    public boolean addRoomIncrementsRoomCount() {
        int roomCount = getSavedRoomCount();

        driver.findElement(createRoomButton).click();

        try {
            Awaitility.await().atMost(Duration.ofSeconds(10)).until(getUpdatedRoomCount(roomCount));
        } catch (ConditionTimeoutException exception) {
            throw new RuntimeException("Failed to save room details");
        }

        return true;
    }

    private Callable<Boolean> getUpdatedRoomCount(int roomCount) {
        return () -> getSavedRoomCount() > roomCount;
    }

    public int getSavedRoomCount() {
        return driver.findElements(savedRoomDetails).size();
    }
}
