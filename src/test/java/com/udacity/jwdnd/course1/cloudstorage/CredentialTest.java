package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CredentialTest {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpass";

    private static final String URL = "testUrl";
    private static final String USER = "testUser";
    private static final String PASS = "testPass";

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
    public void when_create_credential_then_it_is_displayed_on_list() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signup("John", "Smith", USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        homePage.goToCredentialsTab();
        delay();
        homePage.openAddCredentialDialog();
        delay();

        AddCredentialPage addCredentialPage = new AddCredentialPage(driver);
        addCredentialPage.saveCredential(URL, USER, PASS);

        SuccessPage successPage = new SuccessPage(driver);
        successPage.goToHomePage();
        delay();

        homePage.goToCredentialsTab();
        delay();

        assertEquals(URL, homePage.getFirstCredentialUrl());
        assertEquals(USER, homePage.getFirstCredentialUsername());
        assertNotEquals(PASS, homePage.getFirstCredentialPassword());
    }

    @Test
    @Order(2)
    public void when_update_credential_then_it_is_displayed_updated_on_list() {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");

        HomePage homePage = new HomePage(driver);
        homePage.goToCredentialsTab();
        delay();

        homePage.editFirstCredential();
        delay();

        AddCredentialPage addCredentialPage = new AddCredentialPage(driver);
        addCredentialPage.saveCredential(URL + "updated", USER + "updated", PASS + "updated");

        SuccessPage successPage = new SuccessPage(driver);
        delay();
        successPage.goToHomePage();
        delay();

        homePage.goToCredentialsTab();
        delay();

        assertEquals(URL + "updated", homePage.getFirstCredentialUrl());
        assertEquals(USER + "updated", homePage.getFirstCredentialUsername());
        assertNotEquals(PASS + "updated", homePage.getFirstCredentialPassword());
    }

    @Test
    @Order(3)
    public void when_delete_credential_then_it_is_not_displayed_on_list() {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");

        HomePage homePage = new HomePage(driver);
        homePage.goToCredentialsTab();
        delay();

        homePage.deleteFirstCredential();
        delay();

        SuccessPage successPage = new SuccessPage(driver);
        successPage.goToHomePage();
        delay();

        homePage.goToCredentialsTab();
        delay();

        assertTrue(homePage.credentialsAreEmpty());
    }

    private void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}