package qtriptest.tests;

import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class testCase_01 {
    static RemoteWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        System.out.println("Running before Test");
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.manage().window().maximize();
    }

    @Test(description = "User onboarding Flow", groups = "Login Flow", dataProvider = "testcase1",
            dataProviderClass = DP.class, priority = 1)
    public void TestCase01(String userName, String password) throws InterruptedException {
        boolean status;
        HomePage home = new HomePage(driver);
        RegisterPage register = new RegisterPage(driver);
        LoginPage login = new LoginPage(driver);
        home.navigateToHome();
        home.navigateToRegister();
        Thread.sleep(2000);

        // Verify that registration page is displayed.
        assertTrue(driver.getCurrentUrl().endsWith("/register/"),
                "Testcase_01: Failed to navigte to the register page");

        register.registerNewUser(userName, password, true);

        Thread.sleep(1000);

        // Verify if the we are sucessfully navigated to login page.
        assertTrue(driver.getCurrentUrl().endsWith("/login"),
                "Testcase_01: Failed to navigate to login page");

        login.performLogin(register.last_generated_username, password);

        Thread.sleep(2000); // Wait to load the logout button to check if user logged in.

        // Verify if user logged in
        assertTrue(home.isUserLoggedIn(), "Testcase_01: Failed to login user");

        // log out the current user
        home.logOutUser();

        // verify the user logged out.
        assertTrue(home.isUserloggedOut(), "Testcase_01: Failed to logout user");

    }
}
