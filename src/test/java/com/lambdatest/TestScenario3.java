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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScenario3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        ;
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
    public void scenario3() throws InterruptedException {
        String successMsg="Thanks for contacting us, we will get back to you shortly.";
        String expectedError="Fill out this field";

        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/selenium-playground");

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Navigating to input form\", \"level\": \"info\"}}");
        WebElement simpleFormDemo = driver.findElement(By.linkText("Input Form Submit"));
        simpleFormDemo.click();

        //Wait for any element in the page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Submitting form without values\", \"level\": \"info\"}}");
        WebElement submitWebEle= driver.findElement(By.xpath("//form[@id=\"seleniumform\"]//button[@type=\"submit\"]"));
        submitWebEle.click();
        String message = driver.findElement(By.id("name")).getAttribute("validationMessage");
        Assert.assertEquals(message,expectedError);

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Entering all the details\", \"level\": \"info\"}}");
        WebElement nameWebEle= driver.findElement(By.id("name"));
        nameWebEle.sendKeys("Nazeer");
        WebElement emailWebEle= driver.findElement(By.id("inputEmail4"));
        emailWebEle.sendKeys("nazeer31.mohd@gmail.com");
        WebElement passwordWebEle= driver.findElement(By.id("inputPassword4"));
        passwordWebEle.sendKeys("LamdaTesting123");
        WebElement companyWebEle= driver.findElement(By.id("company"));
        companyWebEle.sendKeys("Google");

        WebElement websiteWebEle= driver.findElement(By.id("websitename"));
        websiteWebEle.sendKeys("https://www.lambdatest.com/");

        WebElement countryEle=driver.findElement(By.name("country"));
        Select country=new Select(countryEle);
        country.selectByVisibleText("United States");

        WebElement cityWebEle= driver.findElement(By.id("inputCity"));
        cityWebEle.sendKeys("Hyderabad");
        WebElement add1WebEle= driver.findElement(By.id("inputAddress1"));
        add1WebEle.sendKeys("BHEL");
        WebElement add2WebEle= driver.findElement(By.id("inputAddress2"));
        add2WebEle.sendKeys("Hyderabad");

        WebElement stateWebEle= driver.findElement(By.id("inputState"));
        stateWebEle.sendKeys("Telangana");
        WebElement zipWebEle= driver.findElement(By.id("inputZip"));
        zipWebEle.sendKeys("502032");

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Submitting form\", \"level\": \"info\"}}");
        submitWebEle.click();

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Verifying successful submit\", \"level\": \"info\"}}");
        WebElement successEle=driver.findElement(By.xpath("//p[@class=\"success-msg hidden\"]"));
        Assert.assertEquals(successEle.getText(),successMsg);
        Status = "passed";
        Thread.sleep(800);

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}