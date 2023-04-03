package com.cst438;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EndToEndStudentTest {
    @Autowired
    StudentRepository studentRepository;

    public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";
    public static final String URL = "http://localhost:3000";
    public static final int SLEEP_DURATION = 1000;
    public static final int SLEEP_SHORT = 100;

    @Test
    public void AddStudent() throws Exception {
        String testName = "TestingName";
        String testEmail = "TestingEmail@gmail.com";

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(ops);

        Student x = studentRepository.findByEmail(testEmail);
        assertNull(x);
        try {
            WebElement we;
            driver.get(URL);
            Thread.sleep(SLEEP_DURATION);

            driver.findElement(By.id("AddStudentButton")).click();

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AddStudentDialog")));

            we = driver.findElement(By.id("AddStudentDialog"));

            we.findElement(new By.ByName("studentName")).sendKeys(testName);
            we.findElement(new By.ByName("studentEmail")).sendKeys(testEmail);

            we.findElement(By.id("Add")).click();

            Thread.sleep(SLEEP_SHORT);
        } catch (Exception e) {
            throw new Exception("Was unable to finish the test, an error occured in the selenium new student block\n" + "Exception that occured: " + e.toString());
        }

        x = studentRepository.findByEmail(testEmail);
        assertNotNull(x);

        driver.close();
    }
}
