package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class TestNGScenario2 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    @Parameters({"browser", "url"})
    public void setup(@Optional("chrome") String browser, @Optional("https://www.lambdatest.com/selenium-playground/") String url, Method m) throws MalformedURLException {
        String username = "nazeer90.mohd"; // Your LambdaTest username
        String authkey = "24PIO20uMV8tXcANG8xP6vFbcGsyE1rTjrjM7GtbyblvmID0rT";
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
    public void testCheckboxDemo() {
        SoftAssert softAssert = new SoftAssert();

        // Step 1: Click “Checkbox Demo”
        WebElement checkboxDemoLink = driver.findElement(By.linkText("Checkbox Demo"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("Checkbox Demo")));
        checkboxDemoLink.click();

        // Step 2: Click the checkbox under the “Single Checkbox Demo” section


        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("isAgeSelected")));
        WebElement singleCheckbox = driver.findElement(By.id("isAgeSelected"));
        singleCheckbox.click();

        // Step 3: Validate whether this checkbox is “selected”
        boolean isSelected = singleCheckbox.isSelected();
        softAssert.assertTrue(isSelected, "Checkbox should be selected but it is not.");

        // Step 4: Click the checkbox again and validate whether the checkbox is “unselected”
        singleCheckbox.click();
        boolean isUnselected = !singleCheckbox.isSelected();
        softAssert.assertTrue(isUnselected, "Checkbox should be unselected but it is not.");

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