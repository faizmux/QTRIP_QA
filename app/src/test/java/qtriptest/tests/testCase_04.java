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
import java.util.List;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_04 {
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
        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();
        logStatus("driver", "Initializing driver", "Success");
    }

    @Test(description = "Verify that booking history can be viewed", dataProvider = "testcase4",
            dataProviderClass = DP.class, priority = 4, groups = {"Reliability Flow"})
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2,
            String dataset3) throws InterruptedException, IOException {

        // test1 = reports.startTest("TestCase04", "Verify that booking history can be viewed");
        SoftAssert sa = new SoftAssert();
        HistoryPage history = new HistoryPage(driver);
        HomePage home = new HomePage(driver);
        home.navigateToHome();

        RegisterPage register = new RegisterPage(driver);
        register.navigateToRegisterPage();
        register.registerNewUser(NewUserName, Password, true);
        test1.log(LogStatus.PASS, "User Registration is successful");
        String lastGeneratedUserName = register.last_generated_username;
        LoginPage login = new LoginPage(driver);
        login.performLogin(lastGeneratedUserName, Password);
        test1.log(LogStatus.PASS, "User Login successful");
        String arr[] = {dataset1, dataset2, dataset3};
        String t_id[] = new String[arr.length];

        String cityName, adventureName, GuestName, Date, count;

        AdventurePage adventure = new AdventurePage(driver);
        AdventureDetailsPage adventureDetails = new AdventureDetailsPage(driver);

        for (int i = 0; i < arr.length; i++) {
            home.navigateToHome();
            String arr1[] = arr[i].split(";");
            cityName = arr1[0];
            adventureName = arr1[1];
            GuestName = arr1[2];
            Date = arr1[3];
            count = arr1[4];

            home.searchCity(cityName);
            home.selectCity();

            adventure.searchAdventure(adventureName);
            adventure.clickAdventure();

            adventureDetails.bookAdventure(GuestName, Date, count);
            sa.assertTrue(adventureDetails.isBookingSuccessful(),
                    "TestCase04: Failed to book adventure");
            test1.log(LogStatus.PASS, "Booking Successful for" + cityName);
            history.goToReservationsPage();
            Thread.sleep(3000);
            // t_id[i] = history.getTransactionID(GuestName);
            // System.out.println(t_id[i]);
        }

        // history.goToReservationsPage();

        // for (int i = 0; i < t_id.length; i++) {
        // sa.assertTrue(history.isTranIDpresent(t_id[i]));
        // // test.log(LogStatus.PASS, "Id is present" + t_id[i]);
        // }
        List<WebElement> reservations = history.getReservations();
        sa.assertEquals(reservations.size(), 3,
                "TestCase04: actual booked reservations are different from expected reservations.");
        test1.log(LogStatus.PASS, "Expected Reservations are present on the reservation page");
        sa.assertAll();
        test1.log(LogStatus.INFO, test1.addScreenCapture(ReportSingleton.capture(driver)));
    }
    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        reportSingleton.getReport().endTest(test1);
        reportSingleton.getReport().flush();
        driver.quit();
        reportSingleton.closeReport();
    }


}
