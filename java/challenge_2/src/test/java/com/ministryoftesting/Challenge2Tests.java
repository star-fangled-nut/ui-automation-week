package com.ministryoftesting;

import com.ministryoftesting.components.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Challenge2Tests {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password";
    public static final String BASE_URL = "https://automationintesting.online/";
    protected WebDriver driver;

    @Before
    public void createBrowserInstance() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--headless");
        options.addArguments("--enable-javascript");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    public void loginTest() {
        navigateToAdminPage();
        loginAsAdmin();

        AdminNavBar adminNavBar = new AdminNavBar(driver);

        assertThat(adminNavBar.getFirstNavigationLinkDisplayed(), is("Rooms"));
    }

    @Test
    public void room() {
        navigateToAdminPage();
        loginAsAdmin();

        AddRoomPanel addRoomPanel = new AddRoomPanel(driver);
        addRoomPanel.addRoom("101", "101");

        assertThat(
                "The number of rooms displayed has incremented",
                addRoomPanel.addRoomIncrementsRoomCount(),
                is(true));
    }

    @Test
    public void updateBranding() {
        navigateToSubpage("#/admin/branding");
        loginAsAdmin();

        BrandingPanel brandingPanel = new BrandingPanel(driver);
        brandingPanel.setName("Test");

        assertThat(
                "Branding updated modal is displayed",
                brandingPanel.isBrandingUpdatedModalDisplayed(),
                is(true));
    }

    @Test
    public void contactCheck() {
        navigateToBaseURL();

        ContactPanel contactPanel = new ContactPanel(driver);
        contactPanel.enterContactDetails("TEST123", "TEST123@TEST.COM", "01212121311");
        contactPanel.enterMessageDetails("TEsTEST", "TEsTESTTEsTESTTEsTEST");
        contactPanel.submitMessage();

        assertThat(
                "Contact message submitted successfully",
                contactPanel.isMessageSubmittedSuccessfully(),
                is(true));
    }

    @Test
    public void isTheMessageBoldWhenUnreadInTheMessageView() {
        navigateToSubpage("#/admin/messages");
        loginAsAdmin();

        MessagePanel messagePanel = new MessagePanel(driver);
        assertThat("Unread messages are displayed", messagePanel.unreadMessagesExist(), is(true));
    }

    private void loginAsAdmin() {
        LoginPanel loginPanel = new LoginPanel(driver);
        loginPanel.login(ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    private void navigateToAdminPage() {
        navigateToSubpage("#/admin");
    }

    private void navigateToBaseURL() {
        driver.navigate().to(BASE_URL);
    }

    public void navigateToSubpage(String subpage) {
        driver.navigate().to(BASE_URL + subpage);
    }
}
