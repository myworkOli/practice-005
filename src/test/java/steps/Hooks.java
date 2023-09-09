package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.И;
import org.h2.jdbcx.JdbcDataSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Hooks {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static PageFood pageFood;
    @Before(value="@selenium")
    public void before() {
        driver = new ChromeDriver();
        pageFood= new PageFood(driver);
        wait = new WebDriverWait(driver, Duration.ofMillis(500L));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(ConfProperties.getProperty("url"));

    }
    @After(value="@selenium")
    public static void tearDown() {
        driver.quit();
    }

}
