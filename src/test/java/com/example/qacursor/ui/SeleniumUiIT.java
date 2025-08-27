package com.example.qacursor.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumUiIT {

    @LocalServerPort
    int port;

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        String headless = System.getProperty("headless", System.getenv().getOrDefault("SELENIUM_HEADLESS", "false"));
        if ("true".equalsIgnoreCase(headless)) {
            org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
            options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else {
            driver = new ChromeDriver();
        }
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    void addTask_viaForm() {
        driver.get("http://localhost:" + port + "/");
        WebElement title = driver.findElement(By.id("title"));
        WebElement desc = driver.findElement(By.id("description"));
        WebElement add = driver.findElement(By.id("addBtn"));

        title.sendKeys("UI Task 1");
        desc.sendKeys("added via Selenium");
        add.click();

        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("taskList"), "UI Task 1"));
    }

    @Test
    void validation_showsErrorOnBlankTitle() {
        driver.get("http://localhost:" + port + "/");
        WebElement title = driver.findElement(By.id("title"));
        WebElement add = driver.findElement(By.id("addBtn"));
        // Enter spaces to bypass HTML5 required and trigger server-side validation
        title.sendKeys("   ");
        add.click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("error")));
    }
}


