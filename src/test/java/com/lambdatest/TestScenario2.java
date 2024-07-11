package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestScenario2 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        //String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
       // String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String username ="nazeer31.mohd";
        String authkey ="r4kngtAUMeN1lITWfy76pxd5O4QoekH89MDofo98s4rbh3xgyN";
                String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "macOS Catalina");
        caps.setCapability("browserName", "Chrome");
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
    public void scenario2() throws InterruptedException {
        int outputValue=95;
        int defaultValue=15;
        int repeatTimes=outputValue-defaultValue;

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Opening WebApp\", \"level\": \"info\"}}");
        driver.get("https://www.lambdatest.com/selenium-playground");

        driver.executeScript("lambdatest_executor: {\"action\": \"stepcontext\", \"arguments\": {\"data\": \"Verifying user navigated to correct URL\", \"level\": \"info\"}}");

        WebElement simpleFormDemo = driver.findElement(By.linkText("Drag & Drop Sliders"));
        simpleFormDemo.click();

        //driver.findElement(By.xpath("//h4[text() =' Default value 15']/../div/input")).sendKeys("95");
        WebElement slider = driver.findElement(By.xpath("//h4[text() =' Default value 15']/../div/input"));


        for (int i = 1; i <= repeatTimes ; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }

        String outputValueText=driver.findElement(By.id("rangeSuccess")).getText();

        Assert.assertEquals(Integer.parseInt(outputValueText),outputValue);
        System.out.println("User navigated to correct URL");


        Status = "passed";
        Thread.sleep(150);

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}