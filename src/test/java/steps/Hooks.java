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


    private final int START_ID = 4;

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

    @After(order = 1)
    public void deletFood() {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        String sql = "DELETE FROM FOOD WHERE FOOD_ID > ?";
        int update = jdbcTemplateObject.update(sql, START_ID);

    }


    public static DataSource getH2DataSource() {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(ConfProperties.getProperty("db.host"));
        dataSource.setUser(ConfProperties.getProperty("db.login"));
        dataSource.setPassword(ConfProperties.getProperty("db.password"));


        return dataSource;
    }


    public int getSTART_ID() {
        return START_ID;
    }
}
