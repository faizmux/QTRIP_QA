package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_02 {
    static RemoteWebDriver driver;
    static ExtentReports reports;
    // static ExtentTest 1;
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

    @Test(description = "Verify Search and Filter", dataProvider = "testcase2",
            dataProviderClass = DP.class, priority = 2,
            groups = {"Search and Filter flow"})
    public void TestCase02(String city, String category, String duration,
            String expectedFilterCount, String expectedUnFilterCount) throws InterruptedException, IOException {
        // 1 = reports.startTest("TestCase02", "Verify Search and Filter flow working fine");
        HomePage home = new HomePage(driver);
        AdventurePage adventure = new AdventurePage(driver);
        SoftAssert sa = new SoftAssert();
        // Navigate to home page of qtrip
        home.navigateToHome();
        Thread.sleep(3000);

        // search for a city that is not present
        home.searchCity("America");
        sa.assertFalse(home.AssertAutoCompleteText("America"));
        test1.log(LogStatus.PASS, "Invalid city");

        Thread.sleep(5000);
        home.searchCity(city);
        sa.assertTrue(home.AssertAutoCompleteText(city));

        // Click on the city
        home.selectCity();
        test1.log(LogStatus.PASS, "Valid city");

        // Select filters :: hours
        adventure.clearDuration();
        adventure.setFilterValue(duration);
        // Select Category
        adventure.clearCategory();
        adventure.setCategoryValue(category);

        sa.assertEquals(adventure.getResultCount(), Integer.parseInt(expectedFilterCount),
                "TestCase:02, The actural filtered adventures count is different from expected results.");
        test1.log(LogStatus.PASS, "Filtered result count");
        // Clear filters and category
        adventure.clearCategory();
        adventure.clearDuration();

        sa.assertEquals(adventure.getResultCount(), Integer.parseInt(expectedUnFilterCount),
                "TestCase:02, The actural unfiltered adventures count is different from expected results.");
        test1.log(LogStatus.PASS, "Un-filtered result count");
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
