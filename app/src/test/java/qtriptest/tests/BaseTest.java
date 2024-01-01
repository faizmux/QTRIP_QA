// package qtriptest.tests;

// import java.lang.reflect.Method;
// import com.relevantcodes.extentreports.ExtentReports;
// import com.relevantcodes.extentreports.ExtentTest;
// import com.relevantcodes.extentreports.LogStatus;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.testng.ITestResult;
// import org.testng.annotations.AfterMethod;
// import org.testng.annotations.BeforeMethod;

// public class BaseTest {
//     static RemoteWebDriver driver;
//     static ExtentReports reports;
//     static ExtentTest extentTest;

//     @BeforeMethod(alwaysRun = true)
//     public void beforeTest(Method method)
//     {
//         System.out.println("In the beforeTest");
//         ExtentTestManager.startTest(method.getName());
//     }


//     @AfterMethod(alwaysRun = true)
//     public void afterTest(ITestResult iTestResult)
//     {
//         System.out.println("After method");
//         if(iTestResult.getStatus()==ITestResult.SUCCESS)
//         {
//             ExtentTestManager.testLogger(LogStatus.PASS, "Test is passed.");
//             System.out.println("Inside afterTest Method");
//         }
//         else if(iTestResult.getStatus()==ITestResult.FAILURE)
//         {
//             // Add fail steps screenshot code here.
//             ExtentTestManager.testLogger(LogStatus.FAIL, iTestResult.getThrowable().toString());
//         }
//         else
//         {
//             ExtentTestManager.testLogger(LogStatus.SKIP, "Test is skipped.");
//         }

//         ExtentTestManager.endTest();
//     }
// }
