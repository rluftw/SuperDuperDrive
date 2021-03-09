package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Model.internal.Note;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CloudStorageApplicationTests {
    @LocalServerPort
    private Integer port;
    private WebDriver driver;
    private String baseURL;

    private static String username = "admin";
    private static String password = "password";
    private static String firstName = "will";
    private static String lastName = "smith";


    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    /**
     * Write a Selenium test that verifies that the home page is not accessible without logging in.
     */

    @Test
    public void testUnauthenticatedUser_CanOnlyAccessLoginAndSignUp() {
        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());

        driver.get(baseURL + "/login");
        assertEquals("Login", driver.getTitle());

        driver.get(baseURL + "/signup");
        assertEquals("Sign Up", driver.getTitle());
    }

    /**
     * Write a Selenium test that signs up a new user, logs that user in, verifies that they
     * can access the home page, then logs out and verifies that the home page is no longer accessible.
     */

    @Test
    public void testLoginAfterRegistrationFlow_CanAccessHomeScreen() {
        signUpAndLogin();
        assertEquals("Home", driver.getTitle());

        HomePage page = new HomePage(driver);
        page.logout();

        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());
    }

    /**
     * Write a Selenium test that logs in an existing user, creates a note
     * and verifies that the note details are visible in the note list.
     */

    @Test
    public void testSaveNote_VerifyItsDisplayed() {
        signUpAndLogin();

        String noteTitle = "GME";
        String noteDescription = "TO THE MOOOOON!";

        HomePage homePage = new HomePage(driver);
        homePage.addNewNote(noteTitle, noteDescription);

        driver.get(baseURL + "/home");
        homePage.goToNotesTab();

        Note note = homePage.getFirstNote();
        assertEquals(noteTitle, note.getTitle());
        assertEquals(noteDescription, note.getDescription());
    }

    /**
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the edit note button on an existing note, changes the note data,
     * saves the changes, and verifies that the changes appear in the note list.
     */

    @Test
    public void testEditingNote_VerifyChangesAppear() {
        signUpAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.addNewNote("GME", "TO THE MOOOON!");

        String newTitle = "AMC";
        String newDescription = "TO THE OTHER MOOON!";

        driver.get(baseURL + "/home");
        homePage.editNote(newTitle, newDescription);

        driver.get(baseURL + "/home");
        Note note = homePage.getFirstNote();
        assertEquals(newTitle, note.getTitle());
        assertEquals(newDescription, note.getDescription());
    }

    /**
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the delete note button on an existing note, and verifies that the note
     * no longer appears in the note list.
     */

    @Test
    public void testDeleteNote_VerifyThatACreatedNoteNoLongerExists() {
        signUpAndLogin();

        HomePage homePage = new HomePage(driver);
        homePage.addNewNote("GME", "TO THE MOOOON!");

        driver.get(baseURL + "/home");
        homePage.goToNotesTab();
        assertNotNull(homePage.getFirstNote());

        homePage.deleteNote();

        driver.get(baseURL + "/home");
        homePage.goToNotesTab();
        assertTrue(homePage.isNoteListEmpty());
    }

    private void signUpAndLogin() {
        driver.get(baseURL + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.register(firstName, lastName, username, password);

        driver.get(baseURL + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        driver.get(baseURL + "/home");
    }
}
