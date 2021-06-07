package driver_manager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSetter {

    private static WebDriver driver;

    public static void setDriver() {
        String browserName = System.getProperty("browser");
        if (driver == null) {
            driver = switch (browserName) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    yield new ChromeDriver();
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    yield new FirefoxDriver();
                }
                default -> throw new IllegalStateException("Unexpected value: " + browserName);
            }; // mvn clean test -Denv=dev -Dbrowser=asas
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
