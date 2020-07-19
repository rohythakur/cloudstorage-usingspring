package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NoteTests {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpass";
    private static final String TITLE = "testTitle";
    private static final String DESCRIPTION = "testDescription";

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
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
    @Order(1)
    public void when_create_note_then_it_is_displayed_on_list() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signup("John", "Smith", USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        homePage.goToNotesTab();
        delay();
        homePage.openAddNoteDialog();
        delay();

        AddNotePage addNotePage = new AddNotePage(driver);
        addNotePage.saveNote(TITLE, DESCRIPTION);

        SuccessPage successPage = new SuccessPage(driver);
        successPage.goToHomePage();
        delay();

        homePage.goToNotesTab();
        delay();

        assertEquals(TITLE, homePage.getFirstNoteTitle());
        assertEquals(DESCRIPTION, homePage.getFirstNoteDescription());
    }

    @Test
    @Order(2)
    public void when_update_note_then_it_is_displayed_updated_on_list() {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");

        HomePage homePage = new HomePage(driver);
        homePage.goToNotesTab();
        delay();

        homePage.editFirstNote();
        delay();

        AddNotePage addNotePage = new AddNotePage(driver);
        addNotePage.saveNote(TITLE + "updated", DESCRIPTION + "updated");

        SuccessPage successPage = new SuccessPage(driver);
        delay();
        successPage.goToHomePage();
        delay();

        homePage.goToNotesTab();
        delay();

        assertEquals(TITLE + "updated", homePage.getFirstNoteTitle());
        assertEquals(DESCRIPTION + "updated", homePage.getFirstNoteDescription());
    }

    @Test
    @Order(3)
    public void when_delete_note_then_it_is_not_displayed_on_list() {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");

        HomePage homePage = new HomePage(driver);
        homePage.goToNotesTab();
        delay();

        homePage.deleteFirstNote();
        delay();

        SuccessPage successPage = new SuccessPage(driver);
        successPage.goToHomePage();
        delay();

        homePage.goToNotesTab();
        delay();

        assertTrue(homePage.notesAreEmpty());
    }

    private void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
