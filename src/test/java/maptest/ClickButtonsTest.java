package maptest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClickButtonsTest {

    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(ClickButtonsTest.class.getName());

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://banahills.sunworld.vn/en");
    }

    @Test
    public void clickButtonsAndSearch() {
        String[] buttonSelectors = {
                "a[href*='experience']",
                "a[href*='news']",
                "a[href*='maps']",
                "a[href*='support']"
        };

        for (String selector : buttonSelectors) {
            clickButton(selector);
        }
    }

    private void clickButton(String selector) {
        try {
            WebElement button = driver.findElement(By.cssSelector(selector));
            button.click();
            logger.info("Clicked button: " + selector);


            TimeUnit.SECONDS.sleep(2);
            String expectedUrl = driver.findElement(By.cssSelector(selector)).getAttribute("href");
            String actualUrl = driver.getCurrentUrl();
            assertEquals(expectedUrl, actualUrl, "URL did not match after clicking button: " + selector);

            if (selector.contains("support")) {
                performSupportSearch();
            }

            driver.navigate().back();
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Button not found: " + selector, e);
        }
    }

    private void performSupportSearch() {
        try {
            WebElement searchBox = driver.findElement(By.cssSelector("input[placeholder='Search']"));
            searchBox.sendKeys("How can I buy tickets?");
            WebElement searchButton = driver.findElement(By.cssSelector("button.btn-search.btn"));
            searchButton.click();
            logger.info("Searched for: How can I buy tickets?");


            TimeUnit.SECONDS.sleep(2);
            WebElement searchResults = driver.findElement(By.cssSelector(".search-results"));
            assertTrue(searchResults.isDisplayed(), "Search results are not displayed.");


            String expectedSearchResultText = "How can I buy tickets?";
            String actualSearchResultText = searchResults.getText();
            assertTrue(actualSearchResultText.contains(expectedSearchResultText), "Search results did not contain the expected text.");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Search box or results not found.", e);
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
