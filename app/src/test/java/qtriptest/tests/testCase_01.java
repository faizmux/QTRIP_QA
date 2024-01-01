package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.*;


public class testCase_01 {
    static RemoteWebDriver driver;
    static ExtentReports reports;
    // static ExtentTest test;
    private ReportSingleton reportSingleton;
    static ExtentTest test1;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeSuite(alwaysRun = true, enabled = true)
    public void createDriver() throws MalformedURLException {
        logStatus("driver", "Initializing driver", "Started");
        reportSingleton = ReportSingleton.getInstanceOfSingletonReportClass();
        test1 = reportSingleton.getTest();
        reports = reportSingleton.getReport();
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
        logStatus("driver", "Initializing driver", "Success");
    }

    @Test(description = "User onboarding Flow", dataProvider = "testcase1",
            dataProviderClass = DP.class, priority = 1, groups = {"Login Flow"})
    public void TestCase01(String userName, String password)
            throws InterruptedException, IOException {
        // test1 = reports.startTest("User onboarding Flow");
        boolean status;
        HomePage home = new HomePage(driver);
        RegisterPage register = new RegisterPage(driver);
        LoginPage login = new LoginPage(driver);
        SoftAssert sa = new SoftAssert();
        home.navigateToHome();
        home.navigateToRegister();
        Thread.sleep(2000);

        // Verify that registration page is displayed.
        assertTrue(driver.getCurrentUrl().endsWith("/register/"),
                "Testcase_01: Failed to navigte to the register page");

        status = register.registerNewUser(userName, password, true);
        test1.log(LogStatus.PASS, "User registration successful");
        Thread.sleep(1000);

        // Verify if the we are sucessfully navigated to login page.
        sa.assertTrue(driver.getCurrentUrl().endsWith("/login"),
                "Testcase_01: Failed to navigate to login page");

        login.performLogin(register.last_generated_username, password);

        Thread.sleep(2000); // Wait to load the logout button to check if user logged in.

        // Verify if user logged in
        status = home.isUserLoggedIn();
        test1.log(LogStatus.PASS, "User Logged in Successfully");
        sa.assertTrue(home.isUserLoggedIn(), "Testcase_01: Failed to login user");

        // log out the current user
        home.logOutUser();
        status = home.isUserloggedOut();
        test1.log(LogStatus.PASS, "User logged out successfully");
        // verify the user logged out.
        sa.assertTrue(home.isUserloggedOut(), "Testcase_01: Failed to logout user");
        sa.assertAll();
        test1.log(LogStatus.INFO, test1.addScreenCapture(ReportSingleton.capture(driver)));

        test1.log(LogStatus.PASS, "Test Step Passed");
        reportSingleton.getReport().endTest(test1);
    }

    // @AfterSuite(alwaysRun = true)
    // public void tearDown() {
    // reportSingleton.getReport().endTest(test1);
    // reportSingleton.getReport().flush();
    // driver.quit();
    // reportSingleton.closeReport();
    // }
}
