package qtriptest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class ReportSingleton {
    private static ReportSingleton instanceOfSingletonReportClass = null;
    private ExtentTest test;
    private ExtentReports report;

    private ReportSingleton() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String timestampString = String.valueOf(timestamp.getTime());
        // report = new ExtentReports(System.getProperty("user.dir") + "/OurExtentReport.html");
        report = new ExtentReports(System.getProperty("user.dir") + "/OurExtentReport" + timestampString + ".html", true);
        report.loadConfig(new File(System.getProperty("user.dir") + "/extent_customization_configs.xml"));
        test = report.startTest("Qtrip");
        System.out.println("returned value of test = "+test);
    }

    public static ReportSingleton getInstanceOfSingletonReportClass() {
        if (instanceOfSingletonReportClass == null) {
            instanceOfSingletonReportClass = new ReportSingleton();
        }
        return instanceOfSingletonReportClass;
    }

    public ExtentTest getTest() {
        return test;
    }

    public ExtentReports getReport() {
        return report;
    }

    public static String capture(WebDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File(System.getProperty("user.dir") + "/QTrip_Images/" + System.currentTimeMillis() + ".png");
        String errFilePath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);
        return errFilePath;
    }

    public void closeReport() {
        if (report != null) {
            report.endTest(test);
            report.flush();
            report.close();
        }
    }
}
