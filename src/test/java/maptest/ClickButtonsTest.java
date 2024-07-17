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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClickButtonsTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
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
    @Test
    private void clickButton(String selector) {
        try {
            WebElement button = driver.findElement(By.cssSelector(selector));
            button.click();
            System.out.println("Clicked button: " + selector);

            if (selector.contains("support")) {
                performSupportSearch();
            }

            driver.navigate().back();
        } catch (Exception e) {
            System.out.println("Button not found: " + selector);
        }
    }
    @Test
    private void performSupportSearch() {
        try {
            WebElement searchBox = driver.findElement(By.cssSelector("input[placeholder='Search']"));
            searchBox.sendKeys("How can I buy tickets?");
            WebElement searchButton = driver.findElement(By.cssSelector("button.btn-search.btn"));
            searchButton.click();
            System.out.println("Searched for: How can I buy tickets?");

            // Verify search results are displayed
            TimeUnit.SECONDS.sleep(2); // Wait for results to load
            WebElement searchResults = driver.findElement(By.cssSelector(".search-results"));
            assertTrue(searchResults.isDisplayed(), "Search results are not displayed.");
        } catch (Exception e) {
            System.out.println("Search box or results not found.");
        }
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
