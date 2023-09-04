package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.h2.jdbcx.JdbcDataSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Hooks {


    public static WebDriver driver;
    public static WebDriverWait wait;

    public static DataSource dataSource = getH2DataSource();


    @Before
    public void before() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(500L));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(ConfProperties.getProperty("url"));


    }

    @After(order = 2)
    public static void tearDown() {

        driver.quit();


    }


    public static DataSource getH2DataSource() {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(ConfProperties.getProperty("db.host"));
        dataSource.setUser(ConfProperties.getProperty("db.login"));
        dataSource.setPassword(ConfProperties.getProperty("db.password"));


        return dataSource;
    }



}
