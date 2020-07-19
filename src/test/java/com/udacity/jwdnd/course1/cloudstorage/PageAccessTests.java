package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PageAccessTests {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpass";

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
    public void not_authorized_user_can_access_login_page() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void not_authorized_user_can_access_sign_up_page() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void not_authorized_user_cannot_access_home_page_and_fallback_to_login_page() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void not_authorized_user_cannot_access_success_page_and_fallback_to_login_page() {
        driver.get("http://localhost:" + this.port + "/result/success/message");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void not_authorized_user_cannot_access_error_page_and_fallback_to_login_page() {
        driver.get("http://localhost:" + this.port + "/result/error/message");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void authorized_user_can_access_home_page() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signup("John", "Smith", USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    public void authorized_user_logouts_then_cannot_access_home_page_and_fallback_to_login_page() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signupPage = new SignUpPage(driver);
        signupPage.signup("John", "Smith", USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        homePage.logout();

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
