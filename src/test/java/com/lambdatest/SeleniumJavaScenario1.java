package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

public class SeleniumJavaScenario1 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // LambdaTest credentials
            String username = "nazeer90.mohd"; // Your LambdaTest username
            String authkey = "24PIO20uMV8tXcANG8xP6vFbcGsyE1rTjrjM7GtbyblvmID0rT"; // Your LambdaTest authkey
            String hub = "@hub.lambdatest.com/wd/hub"; // LambdaTest hub URL

            // DesiredCapabilities for Safari on MacOS Catalina
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platform", "MacOS Catalina");  // Platform version
            caps.setCapability("browserName", "Safari");       // Browser name
            caps.setCapability("version", "latest");           // Browser version
            caps.setCapability("visual", true);                // Enable visual testing
            caps.setCapability("video", true);                 // Enable video recording
            caps.setCapability("network", true);               // Enable network logs
            caps.setCapability("terminal", true);              // Enable terminal logs
            caps.setCapability("build", "Selenium Java 101");  // Build name
            caps.setCapability("name", "LambdaTest Test - Safari");  // Test name
            caps.setCapability("plugin", "git-testng");        // Test plugin

            // Initialize RemoteWebDriver with LambdaTest hub URL and desired capabilities
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

            // Open the URL in the browser
            String url = "https://www.lambdatest.com/selenium-playground";
            driver.get(url);
            System.out.println("Opened URL: " + url);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement simpleFormDemoButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Simple Form Demo")));
            simpleFormDemoButton.click();

            // Step 3: Validate that the URL contains “simple-form-demo”
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("simple-form-demo")) {
                System.out.println("URL validation passed: " + currentUrl);
            } else {
                System.out.println("URL validation failed: " + currentUrl);
            }

            // Step 4: Create a variable for a string value
            String message = "Welcome to LambdaTest";

            // Step 5: Enter the message into the "Enter Message" text box
            WebElement messageTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-message")));
            messageTextBox.sendKeys(message);

            // Step 6: Click “Get Checked Value”
            WebElement getCheckedValueButton = driver.findElement(By.xpath("//*[@id='showInput']"));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='showInput']")));
            getCheckedValueButton.click();

            // Step 7: Validate that the same text message is displayed under “Your Message:” section
            WebElement yourMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            String displayedMessage = yourMessage.getText();

            if (displayedMessage.equals(message)) {
                System.out.println("Message validation passed: " + displayedMessage);
            } else {
                System.out.println("Message validation failed: " + displayedMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up - quit the driver
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
