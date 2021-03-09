package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginRegistrationTests {
    @LocalServerPort
    private Integer port;
    private WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testUnauthorizedUser_CanOnlyAccessLoginAndSignUp() {
        driver.get("http://localhost:" + this.port + "/home");
        assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void testLoginAfterRegistrationFlow_CanAccessHomeScreen() {
        String username = "admin";
        String password = "password";

        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.register("will", "smith", username, password);

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        driver.get("http://localhost:" + this.port + "/home");
        assertEquals("Home", driver.getTitle());
    }
}
