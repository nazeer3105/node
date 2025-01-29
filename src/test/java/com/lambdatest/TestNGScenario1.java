package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class TestNGScenario1 {

    private WebDriver driver;
    @BeforeMethod
    @Parameters({"browser", "url"})
    public void setup(@Optional("chrome") String browser, @Optional("https://www.lambdatest.com/selenium-playground/") String url, Method m) throws MalformedURLException {
        String username = "nazeer05.mohdd"; // Your LambdaTest username
        String authkey = "PW5lGwHy0iTl90RuSqHbbZGJNTWWBIF0Go0pfXObDSCqS9ynK3";
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "MacOS Catalina");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("version", "latest");
        caps.setCapability("visual", true);
        caps.setCapability("video", true);
        caps.setCapability("network", true);
        caps.setCapability("terminal", true);
        caps.setCapability("build", "LambdaTest Selenium Playground");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

        // Open the URL
        driver.get(url);
    }

    @Test
    public void validatePageTitle() {
        // Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("body")));

        // Step 2: Get the Page Title
        String pageTitle = driver.getTitle();

        // Step 3: Validate the Page Title (expecting a failure here)
        System.out.println("Validating Page Title...");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(pageTitle, "LambdaTest", "Page title does not match the expected value!");

        // Step 4: Additional steps after the assertion
        System.out.println("Proceeding with the next statement even after assertion failure.");

        // Step 5: Assert all to report any failures
        //softAssert.assertAll();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
