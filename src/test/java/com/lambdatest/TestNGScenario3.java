package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestNGScenario3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

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
        caps.setCapability("build", "LambdaTest Selenium Playground");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

        // Launch URL
        driver.get(url);
    }

    @Test
    public void testJavascriptAlerts() {
        SoftAssert softAssert = new SoftAssert();

        // Step 2: Click “Javascript Alerts”
        WebElement javascriptAlertsLink = driver.findElement(By.linkText("Javascript Alerts"));
        javascriptAlertsLink.click();

        // Step 3: Click the “Click Me” button in the “JavaScript Alerts” section
        WebElement alertButton = driver.findElement(By.xpath("(//*[text()='Click Me'])[1]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//*[text()='Click Me'])[1]")));
        alertButton.click();

        // Step 4: Validate the alert message and click OK
        Alert alert = driver.switchTo().alert();
        String alertMessage = alert.getText();
        softAssert.assertEquals(alertMessage, "I am an alert box!", "Alert message does not match!");

        // Accept the alert
        alert.accept();

        // Assert all validations
        softAssert.assertAll();
        Status = "passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
