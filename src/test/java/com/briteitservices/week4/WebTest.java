package com.briteitservices.week4;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class WebTest {

    private static final int TIMEOUT = 10;
    private static final String USER_HOME = System.getProperty("user.home");

    private static ChromeDriverService service;
    private WebDriver driver;

    @BeforeClass
    public static void startService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(USER_HOME + "/chromedriver/chromedriver"))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterClass
    public static void stopService() {
        service.stop();
    }

    @Before
    public void createDriver() {
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        driver.manage().window().maximize();
    }

    @After
    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testBaidu() throws IOException {
        driver.get("http://www.baidu.com");
        WebElement searchBox = driver.findElement(By.id("kw"));
        searchBox.sendKeys("selenium");
        searchBox.submit();

        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(By.id("foot")));
        assertTrue(driver.getTitle().contains("selenium"));

        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, new File(USER_HOME + "/Desktop/screenshot.png"));
    }
}