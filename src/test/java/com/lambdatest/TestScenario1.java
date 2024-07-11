package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScenario1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "MacOS Catalina");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("version", "latest");
        caps.setCapability("visual", true);
        caps.setCapability("video", true);
        caps.setCapability("network", true);
        caps.setCapability("terminal", true);
        caps.setCapability("build", "Selenium 101 Assignment");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    public void testSimpleFormDemo() throws InterruptedException {
        String userMessage = "Welcome to LambdaTest";

        // Step 1: Open LambdaTest’s Selenium Playground
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Opening WebApp\", \"level\": \"info\"}}");
        driver.get("https://www.lambdatest.com/selenium-playground");

        // Step 2: Click “Simple Form Demo” under Input Forms
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Navigating to Simple Form Demo\", \"level\": \"info\"}}");
        WebElement simpleFormDemo = driver.findElement(By.linkText("Simple Form Demo"));
        simpleFormDemo.click();

        // Step 3: Validate that the URL contains “simple-form-demo”
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Validating URL\", \"level\": \"info\"}}");
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("simple-form-demo"), "URL does not contain expected text");

        // Step 4: Create a variable for a string value
        // Step 5: Use this variable to enter values in the “Enter Message” text box
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Entering text\", \"level\": \"info\"}}");
        WebElement userMessageElement = driver.findElement(By.id("user-message"));
        userMessageElement.sendKeys(userMessage);

        // Step 6: Click “Get Checked Value”
        driver.findElement(By.id("showInput")).click();

        // Step 7: Validate whether the same text message is displayed in the right-hand panel under the “Your Message:” section
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Validating displayed message\", \"level\": \"info\"}}");
        String message = driver.findElement(By.id("message")).getText();
        Assert.assertEquals(message, userMessage, "Displayed message does not match the entered message");

        Status = "passed";
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Adding Test Result and Closing Browser\", \"level\": \"info\"}}");
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}
