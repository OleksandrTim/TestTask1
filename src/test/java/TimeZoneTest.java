import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeZoneTest {

    private WebDriver driver;


    @BeforeMethod
    public void testsSetUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.livescore.com/en/");
    }

    @Test
    public void test1CheckThatTheTimeOfTheEventHasChangedAfterTheTimeZoneChange(){
        driver.findElement(By.xpath("//*[@id='simpleCookieBarCloseButton']")).click();
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@id,'__match-row')]//div[@class='Go']"));
        System.out.println("Number of elements:" + elements.size());
        elements.get(3).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String eventStartTime = driver.findElement(By.xpath("//span[@id='score-or-time']")).getText();
        System.out.println("Current Time:" + eventStartTime);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String eventStartDate = driver.findElement(By.xpath("//span[@id='SEV__status']")).getText();
        System.out.println("Current Date:" + eventStartDate);

        driver.findElement(By.xpath("//span[@id='burger-menu-open']")).click();
        driver.findElement(By.xpath("//span[normalize-space()='Settings']")).click();
        driver.findElement(By.xpath("//label[@id='TZ_SELECT-label']")).click();
        driver.findElement(By.xpath("//div[@id='TZ_SELECT__4']")).click();
        driver.findElement(By.xpath("//button[normalize-space()='Apply']")).click();

        String eventStartTimeAfterChangingTimeZone = driver.findElement(By.xpath("//span[@id='score-or-time']")).getText();
        System.out.println("New Time:" + eventStartTimeAfterChangingTimeZone);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        String eventStartDateAfterChangingTimeZone = driver.findElement(By.xpath("//span[@id='SEV__status']")).getText();
        System.out.println("New Date:" + eventStartDateAfterChangingTimeZone);

        Assert.assertNotEquals(eventStartDateAfterChangingTimeZone, eventStartTime);


    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

}
