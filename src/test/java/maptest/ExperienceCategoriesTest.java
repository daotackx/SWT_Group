package maptest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ExperienceCategoriesTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testClickElements() {

        driver.get("https://banahills.sunworld.vn/en/gioi-thieu");
        System.out.println("Navigated to https://banahills.sunworld.vn/en/gioi-thieu");

        try {

            WebElement linkElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".list-box .box a")));
            assertTrue("Link element should be displayed", linkElement.isDisplayed());
            linkElement.click();
            System.out.println("Clicked on .list-box .box a");
            Thread.sleep(2000);


            WebElement newsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-news .more")));
            assertTrue("News element should be displayed", newsElement.isDisplayed());
            System.out.println("Verified .box-news .more is displayed");


            newsElement.click();
            System.out.println("Clicked on .box-news .more");
            Thread.sleep(2000);


            WebElement zoomIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".icon.icon-zoom")));
            assertTrue("Zoom icon should be displayed", zoomIcon.isDisplayed());
            System.out.println("Verified .icon.icon-zoom is displayed");


            zoomIcon.click();
            System.out.println("Clicked on .icon.icon-zoom");
            Thread.sleep(2000);


            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cboxNext")));
            assertTrue("Next button should be displayed", nextButton.isDisplayed());
            nextButton.click();
            System.out.println("Clicked on #cboxNext");
            Thread.sleep(2000);


            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cboxClose")));
            assertTrue("Close button should be displayed", closeButton.isDisplayed());
            closeButton.click();
            System.out.println("Clicked on #cboxClose");
            Thread.sleep(2000); //


            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            System.out.println("Scrolled to the bottom of the page");
            Thread.sleep(2000);


            WebElement homeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("menu-item-1705")));
            assertTrue("Home button should be displayed", homeButton.isDisplayed());
            homeButton.click();
            System.out.println("Clicked on Home button");
            Thread.sleep(2000);


            wait.until(ExpectedConditions.urlToBe("https://banahills.sunworld.vn/en"));
            assertEquals("URL should be the homepage", "https://banahills.sunworld.vn/en", driver.getCurrentUrl());
            System.out.println("Verified return to homepage");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
