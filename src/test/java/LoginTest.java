import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Disable notifications
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSunworldLogin() {
        // Open Sunworld booking page
        driver.get("https://booking.sunworld.vn/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click dang nhap
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Đăng nhập')]")));
        searchIcon.click();

        // input username
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        emailField.sendKeys("duyenvtmde170612@fpt.edu.vn");

        // enter password
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        passwordField.sendKeys("Abcd123@");

        // click dang nhap
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='ant-btn ant-btn-primary StyledButton-sc-1jxoc01-0 jSCeLU']"))); // Adjust selector as needed
        loginButton.click();

        WebElement userAccount = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, 'reapop__notification-message') and contains(text(), 'Chào mừng Quý khách Võ thị mỹ duyên đã đăng nhập thành công')]"))); // Adjust selector as needed
        assertTrue(userAccount.isDisplayed());

        WebElement success = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, 'reapop__notification-message') and contains(text(), 'Thành công')]"))); // Adjust selector as needed
        assertTrue(success.isDisplayed());
    }

    @Test
    public void testSunworldLoginIncorrectPassword() {
        // Open Sunworld booking page
        driver.get("https://booking.sunworld.vn/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click dang nhap
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Đăng nhập')]")));
        loginButton.click();

        //  enter email
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        emailField.sendKeys("duyenvtmde170612@fpt.edu.vn");

        // enter incorrect password
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        passwordField.sendKeys("duyen");

        // click dnhap
        WebElement loginButtonn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='ant-btn ant-btn-primary StyledButton-sc-1jxoc01-0 jSCeLU']"))); // Adjust selector as needed
        loginButtonn.click();

        WebElement errorMessage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[contains(@class, 'reapop__notification-message') and contains(text(), 'Vui lòng nhập đúng email hoặc password')]"))); // Adjust selector as needed
        assertTrue(errorMessage.isDisplayed());
    }

    @Test
    public void testSunworldLoginEmptyFields() {
        driver.get("https://booking.sunworld.vn/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
// Click dang nhap
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Đăng nhập')]")));
        loginButton.click();

        WebElement loginButtonConfirm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='ant-btn ant-btn-primary StyledButton-sc-1jxoc01-0 jSCeLU']"))); // Adjust selector as needed
        loginButtonConfirm.click();

        WebElement errorMessagePass = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='alert' and contains(@class, 'ant-form-item-explain-error') and contains(text(), 'Mật khẩu không được để trống')]"))); // Adjust selector as needed
        assertTrue(errorMessagePass.isDisplayed());

        WebElement errorMessageUser = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='alert' and contains(@class, 'ant-form-item-explain-error') and contains(text(), 'Email không được để trống')]"))); // Adjust selector as needed
        assertTrue(errorMessageUser.isDisplayed());

        String title = driver.getTitle();
        assertEquals("Sun Tickets", title);
    }

}

