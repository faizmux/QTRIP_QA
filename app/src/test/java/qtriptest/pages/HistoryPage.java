
package qtriptest.pages;

import qtriptest.SeleniumWrapper;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {
    RemoteWebDriver driver;

    @FindBy(xpath = "//a[text()='Reservations']")
    WebElement reservationsButton;

    public HistoryPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    // Navigate to reservations page
    public boolean goToReservationsPage() {
        try {
            SeleniumWrapper.click(reservationsButton, driver);
            //reservationsButton.click();
        } catch (Exception e) {
            System.out.println(e);
        }
        return driver.getCurrentUrl().endsWith("/adventures/reservations/index.html");
    }

    // Count the reservations
    public List<WebElement> getReservations() {
        List<WebElement> reservations = new ArrayList<>();
        try {
            reservations =
                    driver.findElements(By.xpath("//tbody[@id='reservation-table']//child::tr"));
            return reservations;
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
        return reservations;
    }

    public String getTransactionID(String adventureName) throws InterruptedException{
        Thread.sleep(3000);
        List<WebElement> reservations = this.getReservations();
        String transactionID = "";
        if (reservations.size() != 0) {
            for (WebElement reservation : reservations) {
                try {
                    WebElement adventureNameEle = reservation
                            .findElement(By.xpath("//child::td[text()='" + adventureName + "']"));
                    if (adventureNameEle.getText().equals(adventureName)) {
                        return transactionID = reservation.findElement(By.tagName("th")).getText();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        }
        System.out.println(transactionID);
        return transactionID;
    }

    // Check if transaction id is present
    public boolean isTranIDpresent(String transactionID) {

        // List<WebElement> reservations = this.getReservations();

        // if (reservations.size() != 0) {

        // for (WebElement reservation : reservations) {
        // try {
        // WebElement adventureNameEle = reservation.findElement(By.xpath("//child::th"));
        // if (adventureNameEle.getText().equals(transactionID)) {
        // return false;
        // }
        // } catch (Exception e) {
        // System.out.println(e);
        // }
        // }

        // }

        // return true;
        String pageSource = driver.getPageSource();
        if (pageSource.contains(transactionID)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean cancelReservation(String transactionID) {

        if (transactionID != null) {
            try {
                WebElement cancelButton =
                        driver.findElement(By.xpath("//button[@id='" + transactionID + "']"));
                SeleniumWrapper.click(cancelButton, driver);
                //cancleButton.click();
                return true;

            } catch (NoSuchElementException e) {
                System.out.println(e);
            }
            return true;
        } else {
            return false;
        }
    }
}
