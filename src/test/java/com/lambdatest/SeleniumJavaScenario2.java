package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class SeleniumJavaScenario2 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // LambdaTest credentials
            String username = "nazeer05.mohdd"; // Your LambdaTest username
            String authkey = "PW5lGwHy0iTl90RuSqHbbZGJNTWWBIF0Go0pfXObDSCqS9ynK3";
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
            caps.setCapability("name", "LambdaTest Test - Slider");  // Test name
            caps.setCapability("plugin", "git-testng");        // Test plugin

            // Initialize RemoteWebDriver with LambdaTest hub URL and desired capabilities
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

            // Open the URL in the browser
            String url = "https://www.lambdatest.com/selenium-playground";
            driver.get(url);
            System.out.println("Opened URL: " + url);

            // Step 1: Click on "Drag & Drop Sliders" under "Progress Bars & Sliders"
            WebElement dragDropSliderLink = driver.findElement(By.linkText("Drag & Drop Sliders"));
            dragDropSliderLink.click();
            System.out.println("Clicked on 'Drag & Drop Sliders'");

            // Step 2: Select the slider and drag it to make the value 95
            WebElement slider = driver.findElement(By.id("slider1"));

            // Validate initial value (should be 15)
            WebElement sliderValue = driver.findElement(By.id("range"));
            String initialValue = sliderValue.getText();
            System.out.println("Initial slider value: " + initialValue);

            // Use Actions to drag the slider
            Actions actions = new Actions(driver);
            actions.dragAndDropBy(slider, 80, 0).perform();  // Adjust to drag slider towards value 95
            System.out.println("Dragged slider towards value 95");

            // Validate if the slider value is 95
            String finalValue = sliderValue.getText();
            if (finalValue.equals("95")) {
                System.out.println("Slider value validated successfully: " + finalValue);
            } else {
                System.out.println("Slider value validation failed. Current value: " + finalValue);
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

