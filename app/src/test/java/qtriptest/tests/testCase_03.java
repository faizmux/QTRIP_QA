package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_03 {
    static RemoteWebDriver driver;
    static ExtentReports reports;
    // static ExtentTest test;
    private ReportSingleton reportSingleton;
    static ExtentTest test1;

        public static void logStatus(String type, String message, String status) {
                System.out.println(String.format("%s |  %s  |  %s | %s",
                                String.valueOf(java.time.LocalDateTime.now()), type, message,
                                status));
        }

        @BeforeSuite(alwaysRun = true, enabled = true)
        public void createDriver() throws MalformedURLException {
            logStatus("driver", "Initializing driver", "Started");
            reportSingleton = ReportSingleton.getInstanceOfSingletonReportClass();
            test1 = reportSingleton.getTest();
            DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
            driver = singleton.getDriver();
            logStatus("driver", "Initializing driver", "Success");
        }

        @Test(description = "Verify the adventure booking and cancellation flow",
                        dataProvider = "testcase3", dataProviderClass = DP.class, priority = 3,
                        groups = {"Booking and Cancellation Flow"})
        public void TestCase03(String userName, String password, String cityName,
                        String adventureName, String GuestName, String Date, String count)
                        throws InterruptedException, IOException {

                //test1 = reports.startTest("Verify the adventure booking and cancellation flow");
                test1 = reportSingleton.startTest("Verify the adventure booking and cancellation flow");

                SoftAssert sa = new SoftAssert();
                WebDriverWait wait = new WebDriverWait(driver, 15);
                RegisterPage register = new RegisterPage(driver);

                // Create a new User
                register.navigateToRegisterPage();
                wait.until(ExpectedConditions.urlContains("/register/"));// Wait to load the
                                                                         // register page.

                register.registerNewUser(userName, password, true);
                test1.log(LogStatus.PASS, "User Registered Successfully");

                LoginPage login = new LoginPage(driver);
                // Login with new user
                wait.until(ExpectedConditions.urlContains("/login"));// Wait to load the login page.
                String lastGeneratedUserName = register.last_generated_username;
                login.performLogin(lastGeneratedUserName, password);
                test1.log(LogStatus.PASS, "User Login successfully");
                // Thread.sleep(1000);

                HomePage home = new HomePage(driver);
                // Search for a city that is present
                wait.until(ExpectedConditions
                                .visibilityOfElementLocated(By.xpath("//div[@id='data']")));
                home.searchCity(cityName);
                // Wait to auto complete the text.
                Thread.sleep(200);
                // Click on the city
                home.selectCity();

                AdventurePage adventure = new AdventurePage(driver);
                adventure.selectAdventure(adventureName);

                // Assert.assertTrue(adventureDetails.isInAdventureDetailPage(adventureName),
                // "TestCase03: Wrong adventure page found");

                AdventureDetailsPage adventureDetails = new AdventureDetailsPage(driver);
                // Book an adventure
                adventureDetails.bookAdventure(GuestName, Date, count);
                wait.until(ExpectedConditions.invisibilityOf(
                                driver.findElement(By.xpath("//button[text()='Reserve']"))));

                Thread.sleep(2000);

                sa.assertTrue(adventureDetails.isBookingSuccessful(),
                                "TestCase03: Failed to book adventure");
                test1.log(LogStatus.PASS, "Adventure booking successful");

                HistoryPage history = new HistoryPage(driver);
                history.goToReservationsPage();
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//h1[text()='Your Reservations']")));

                // Note down the transaction ID

                driver.navigate().refresh();
                String transID = history.getTransactionID(adventureName);
                System.out.println(transID);



                // Thread.sleep(2000);
                // Cancel the reservation
                boolean isResvCancel = history.cancelReservation(transID);
                sa.assertTrue(isResvCancel, "TestCase03: Failed to cancel the reservation");
                // Refresh the history page
                test1.log(LogStatus.PASS, "Reservation is cancelled");

                Thread.sleep(2000);
                // Check if the transaction ID is removed.
                // removed - if it is removed - cancelled successful - true
                boolean isTranIDremoved = history.isTranIDpresent(transID);
                sa.assertTrue(isTranIDremoved,
                                "TestCase03: Failed to cancel the reservation, the transaction ID is still there.");
                test1.log(LogStatus.PASS, "TransactionID is not present");
                sa.assertAll();
                test1.log(LogStatus.INFO, test1.addScreenCapture(ReportSingleton.capture(driver)));
                reportSingleton.getReport().endTest(test1);
        }

        // @AfterSuite(alwaysRun = true)
        // public void tearDown() {
        //     reportSingleton.getReport().flush();
        //     driver.quit();
        //     reportSingleton.closeReport();
        // }

}

