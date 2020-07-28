package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://localhost/litecart/admin/.");
        driver.findElement(By.xpath("//input[@type=\"text\"]")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@value=\"Login\"]")).click();
        wait.until(titleIs("My Store"));
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        wait.until(titleIs("Countries | My Store"));
        driver.findElement(By.xpath("//a[@class='button']")).click();
        List<WebElement> links = driver.findElements(By.xpath("//form//a[@target='_blank']"));
        String defaultWindow = driver.getWindowHandle();
        for (int i = 0; i < links.size(); i++) {
            List<WebElement> updatedLinks = driver.findElements(By.xpath("//form//a[@target='_blank']"));
            updatedLinks.get(i).click();
            Set<String> windowSet = driver.getWindowHandles();
            windowSet.remove(defaultWindow);
            driver.switchTo().window(windowSet.iterator().next());
            driver.close();
            driver.switchTo().window(defaultWindow);
        }
    }

    @After
    public void stop(){
        driver.quit();
        driver= null;
    }
}