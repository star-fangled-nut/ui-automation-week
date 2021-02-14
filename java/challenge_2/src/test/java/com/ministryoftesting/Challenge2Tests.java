package com.ministryoftesting;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
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

    WebDriver driver;

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
        driver.navigate().to("https://automationintesting.online/#/");
        driver.findElement(By.cssSelector("footer p a:nth-child(5)")).click();
        driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin");
        driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
        driver.findElement(By.className("float-right")).click();

        List<WebElement> navigationLinks = driver.findElements(By.className("nav-item"));
        System.out.println(navigationLinks.get(0).getText());

        assertThat(navigationLinks.get(0).getText(), is("Rooms"));
    }

    //  Test two: Check to see if rooms are saved and displayed in the UI
    @Test
    public void room() {
        driver.navigate().to("https://automationintesting.online/#/");
        driver.findElement(By.xpath("//a[@href=\"/#/admin\"]")).click();


        driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin");
        driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
        driver.findElement(By.className("float-right")).click();

        driver.findElement(By.cssSelector(".room-form > div:nth-child(1) input")).sendKeys("101");
        driver.findElement(By.cssSelector(".room-form > div:nth-child(4) input")).sendKeys("101");
        driver.findElement(By.className("btn-outline-primary")).click();

        assertThat(driver.findElements(By.className(".detail")).size(), not(1));
    }

    //  Test three: Check to see the confirm message appears when branding is updated
    @Test
    public void updateBranding() {
        driver.get("https://automationintesting.online/#/admin");

        driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin");
        driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
        driver.findElement(By.className("float-right")).click();

        driver.get("https://automationintesting.online/#/admin/branding");

        driver.findElement(By.id("name")).sendKeys("Test");
        driver.findElement(By.className("btn-outline-primary")).click();

        int count = driver.findElements(By.xpath("//button[text()=\"Close\"]")).size();

        if(count == 1){
            assertThat(true, is(true));
        } else {
            assertThat(true, is(Boolean.FALSE));
        }
    }

    //  Test four: Check to see if the contact form shows a success message
    @Test
    public void contactCheck() {
        driver.navigate().to("https://automationintesting.online");

        driver.findElement(By.cssSelector("input[placeholder=\"Name\"]")).sendKeys("TEST123");
        driver.findElement(By.cssSelector("input[placeholder=\"Email\"]")).sendKeys("TEST123@TEST.COM");
        driver.findElement(By.cssSelector("input[placeholder=\"Phone\"]")).sendKeys("01212121311");
        driver.findElement(By.cssSelector("input[placeholder=\"Subject\"]")).sendKeys("TEsTEST");
        driver.findElement(By.cssSelector("textarea")).sendKeys("TEsTESTTEsTESTTEsTEST");
        driver.findElement(By.xpath("//button[text()=\"Submit\"]")).click();

        Awaitility.await().atMost(Duration.ofSeconds(10)).until(this::submitButtonIsNoLongerDisplayed);
}

    public boolean submitButtonIsNoLongerDisplayed() {
        return (driver.findElement(By.className("col-sm-5")).getText().contains("Thanks for getting in touch"));
    }

    //  Test five: Check to see if unread messages are bolded
    @Test
    public void isTheMessageBoldWhenUnreadInTheMessageView() {
        driver.navigate().to("https://automationintesting.online/#/admin/messages");

        driver.findElement(By.xpath("//div[@class=\"form-group\"][1]/input")).sendKeys("admin");
        driver.findElement(By.xpath("//div[@class=\"form-group\"][2]/input")).sendKeys("password");
        driver.findElement(By.className("float-right")).click();

        assertThat(checkCount(driver.findElements(By.cssSelector(".read-false"))), is(true));
    }

    private Boolean checkCount(List<WebElement> elements) {
        return elements.size() >= 1;
    }
}
