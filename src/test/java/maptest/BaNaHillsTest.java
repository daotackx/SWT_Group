package maptest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BaNaHillsTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = Logger.getLogger(BaNaHillsTest.class.getName());

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testFilterCareer() {
        try {

            navigateToUrl("https://banahills.sunworld.vn/en/category/news-da-nang");


            WebElement filterDropdownButton = waitForElementToBeClickable(By.id("dropdownMenuButton"));
            clickElement(filterDropdownButton);


            WebElement careerOption = waitForElementToBeClickable(By.xpath("//a[@class='dropdown-item' and @href='https://banahills.sunworld.vn/en/category/career']"));
            clickElement(careerOption);


            WebElement result = waitForElementToBeVisible(By.cssSelector(".box-news"));
            assertTrue("The result element should be displayed", result.isDisplayed());


            String expectedUrl = "https://banahills.sunworld.vn/en/category/career";
            String actualUrl = driver.getCurrentUrl();
            assertEquals("The URL should be the career category page", expectedUrl, actualUrl);


            WebElement header = waitForElementToBeVisible(By.tagName("h1"));
            assertEquals("The header should be 'Career'", "Career", header.getText());

        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during the test", e);
            throw e;
        }
    }

    private void navigateToUrl(String url) {
        driver.get(url);
        logger.info("Navigated to " + url);
        sleep(3000);
    }

    private WebElement waitForElementToBeClickable(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        assertTrue("Element should be displayed", element.isDisplayed());
        return element;
    }

    private WebElement waitForElementToBeVisible(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        assertTrue("Element should be displayed", element.isDisplayed());
        return element;
    }

    private void clickElement(WebElement element) {
        element.click();
        logger.info("Clicked on element: " + element);
        sleep(2000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Thread was interrupted", e);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }
}
