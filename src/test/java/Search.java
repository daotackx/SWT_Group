import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Search {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testSearchFunctionality() {
        // Open the Sun World Ba Na Hills website
        driver.get("https://banahills.sunworld.vn/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the notification modal to appear and close it
        try {
            WebElement notificationCloseButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".close-notification-button"))); // Adjust the locator as needed
            notificationCloseButton.click();
        } catch (Exception e) {
            System.out.println("Notification modal not found or already closed.");
        }

        // Locate the search input box
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q"))); // Adjust the 'name' attribute if it's different
        searchBox.sendKeys("Fairy Blossom");

        // Submit the search form
        searchBox.submit();

        // Wait for the search results to load (adjust the locator as necessary)
        WebElement searchResults = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".search-results-container"))); // Use the actual class or ID

        // Assert that search results contain the expected text
        assertTrue(searchResults.getText().toLowerCase().contains("fairy blossom"));

        // Close the browser
        driver.quit();
    }
}
