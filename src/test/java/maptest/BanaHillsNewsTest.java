package maptest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BanaHillsNewsTest {
    private WebDriver driver;
    private static final Logger logger = Logger.getLogger(BanaHillsNewsTest.class.getName());

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }

    @Test
    void testScrollAndNavigatePages() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            driver.get("https://banahills.sunworld.vn/en/category/news-da-nang");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            WebElement boxNewsType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-news-type")));
            assertTrue(boxNewsType.isDisplayed(), ".box-news-type is displayed");

            List<WebElement> nextButtons = driver.findElements(By.className("nextpostslink"));
            if (!nextButtons.isEmpty()) {
                WebElement nextButton = nextButtons.get(0);
                nextButton.click();
                Thread.sleep(2000);

                wait.until(ExpectedConditions.urlContains("/page/"));
                boxNewsType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-news-type")));
                assertTrue(boxNewsType.isDisplayed(), ".box-news-type is displayed after navigating to the next page");

                String expectedTitle = "News – Page 2 – SUN WORLD BA NA HILLS";
                assertEquals(expectedTitle, driver.getTitle(), "Page title is correct after navigating to the next page");
            } else {

                fail("No next page button found or an error occurred");
            }


        } finally {
            driver.quit();
        }
    }

}
