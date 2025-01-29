package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class SeleniumJavaScenario3 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // LambdaTest credentials
            String username = "nazeer90.mohd"; // Your LambdaTest username
            String authkey = "24PIO20uMV8tXcANG8xP6vFbcGsyE1rTjrjM7GtbyblvmID0rT";
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
            caps.setCapability("name", "LambdaTest Test - Form Submit");  // Test name
            caps.setCapability("plugin", "git-testng");        // Test plugin

            // Initialize RemoteWebDriver with LambdaTest hub URL and desired capabilities
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

            // Open the URL in the browser
            String url = "https://www.lambdatest.com/selenium-playground";
            driver.get(url);
            System.out.println("Opened URL: " + url);

            // Step 1: Click on "Input Form Submit"
            WebElement inputFormSubmitLink = driver.findElement(By.linkText("Input Form Submit"));
            inputFormSubmitLink.click();
            System.out.println("Clicked on 'Input Form Submit'");

            // Step 2: Click "Submit" without filling any fields
            WebElement submitButton = driver.findElement(By.xpath("//*[text()='Submit']"));
            submitButton.click();
            System.out.println("Clicked 'Submit' without filling the form");

            // Step 3: Assert "Please fill out this field." error message
            WebElement nameFieldError = driver.findElement(By.id("name"));
            String errorMessage = nameFieldError.getAttribute("validationMessage");
            if (errorMessage.equals("Please fill out this field.")) {
                System.out.println("Validation error for Name field: " + errorMessage);
            }

            // Step 4: Fill in Name, Email, and other fields
            driver.findElement(By.id("name")).sendKeys("John Doe");
            driver.findElement(By.xpath("(//*[@type='email'])[2]")).sendKeys("johndoe@example.com");
            driver.findElement(By.xpath("//*[@name='password']")).sendKeys("This is a test message.");

            // Step 5: Select "United States" from the Country drop-down using the text property
            Select countryDropdown = new Select(driver.findElement(By.xpath("//*[@name='country']")));
            countryDropdown.selectByVisibleText("United States");
            System.out.println("Selected 'United States' from the country dropdown");

            // Step 6: Click "Submit"
            submitButton.click();
            System.out.println("Form submitted");

            // Step 7: Validate success message
            WebElement successMessage = driver.findElement(By.xpath("//*[contains(text(), 'Thanks for contacting us, we will get back to you shortly.')]"));
            if (successMessage.isDisplayed()) {
                System.out.println("Success message validated: " + successMessage.getText());
            } else {
                System.out.println("Success message not displayed.");
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
