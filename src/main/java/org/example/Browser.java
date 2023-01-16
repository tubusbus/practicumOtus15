package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Config;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Browser {

    //    FirefoxDriver fireFoxDriver = new FirefoxDriver();

//    ChromeOptions options = new ChromeOptions();
    public WebDriver runDriwer() {
        if (getRemoteUrl() == null) {
            downloadLocalWebDriver(DriverManagerType.CHROME);
            return new ChromeDriver(setOptions());
        } else
            return new RemoteWebDriver(getRemoteUrl(), setOptions());
    }

    private URL getRemoteUrl() {
        try {
            return new URL(System.getProperty("webdriver.remote.url"));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private void downloadLocalWebDriver(DriverManagerType driverType) {
        Config wdmConfig = WebDriverManager.chromedriver().config();
        wdmConfig.setAvoidBrowserDetection(true);

        String browserVersion = System.getProperty("browser.version", "");

        if (!browserVersion.isEmpty()) {
            wdmConfig.setChromeDriverVersion(browserVersion);
        }
        WebDriverManager.getInstance(driverType).setup();
    }


    public ChromeOptions setOptions() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments();
//        Map<String, Object> prefs = new HashMap<>();
//        List<String> args = new ArrayList<>();
//        options.addArguments(args);
        options.addArguments("--no-sandbox");
        options.addArguments("--no-first-run");
        options.addArguments("--enable-extensions");
        options.addArguments("--homepage=about:blank");
        options.addArguments("--start-maximized");
        options.addArguments("--lang=ru");
//        options.addArguments("--whitelisted-ips=""");
        options.addArguments("--ignore-certificate-errors");
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.VERSION, System.getProperty("browser.version", "107.0"));
        options.setCapability(CapabilityType.BROWSER_NAME, System.getProperty("browser", "chrome"));
        options.setCapability("enableVNC", Boolean.parseBoolean(System.getProperty("enableVNC", "false")));
//        options.setHeadless(HEADLESS);
//        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        return options;
    }
}
