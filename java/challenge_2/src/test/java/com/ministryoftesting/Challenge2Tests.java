package com.ministryoftesting;

import com.ministryoftesting.components.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

//    Welcome to UI Automation Challenge 2
//
//    For this challenge it's all about creating clean, readable and maintainable code. Below
//    are five tests that work (just about) but require cleaning up. Update this class so that
//    code base so that it's easier to maintain, more readable and has sensible ways of asserting
//    data. You might want to research different approaches to improving UI automation such as
//    Page Object Models and implicit vs. explicit waits

public class Challenge2Tests {

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password";
    public static final String BASE_URL = "https://automationintesting.online/";
    protected WebDriver driver;

    @Before
    public void createBrowserInstance() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    //  Test one: Check to see if you can log in with valid credentials
    @Test
    public void loginTest() {
        navigateToAdminPage();
        loginAsAdmin();

        AdminNavBar adminNavBar = new AdminNavBar(driver);

        System.out.println(adminNavBar.getNavigationLinkText().get(0));
        assertThat(adminNavBar.getNavigationLinkText().get(0), is("Rooms"));
    }

    //  Test two: Check to see if rooms are saved and displayed in the UI

    @Test
    public void room() {
        navigateToAdminPage();
        loginAsAdmin();

        AddRoomPanel addRoomPanel = new AddRoomPanel(driver);
        addRoomPanel.addRoom("101", "101");

        assertThat(addRoomPanel.getSavedRoomCount(), not(1));
    }
    //  Test three: Check to see the confirm message appears when branding is updated

    @Test
    public void updateBranding() {
        navigateToSubpage("#/admin/branding");
        loginAsAdmin();

        BrandingPanel brandingPanel = new BrandingPanel(driver);
        brandingPanel.setName("Test");

        assertThat(
                "Branding updated modal is displayed",
                brandingPanel.isCloseModalButtonDisplayed(),
                is(true));
    }
    //  Test four: Check to see if the contact form shows a success message

    @Test
    public void contactCheck() {
        navigateToBaseURL();

        ContactPanel contactPanel = new ContactPanel(driver);
        contactPanel.enterContactDetails("TEST123", "TEST123@TEST.COM", "01212121311");
        contactPanel.enterMessageDetails("TEsTEST", "TEsTESTTEsTESTTEsTEST");
        contactPanel.submitMessage();

        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .until(contactPanel::messageIsSubmittedSuccessfully);
    }

    //  Test five: Check to see if unread messages are bolded
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
