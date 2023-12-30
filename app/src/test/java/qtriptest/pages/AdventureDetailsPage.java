package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {
    RemoteWebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    WebElement guestNameTextBox;

    @FindBy(xpath = "//input[@name='date']")
    WebElement dateBox;

    @FindBy(xpath = "//input[@name='person']")
    WebElement personCountBox;

    @FindBy(xpath = "//button[text()='Reserve']")
    WebElement reserveButton;

    // @FindBy(xpath = "//div[@id='reserved-banner']")
    // WebElement messageElement;

    // @FindBy(className = "alert-success")
    // WebElement messageElement;

    @FindBy(xpath = "//div[contains(text(),'Greetings! Reservation for this adventure is successful')]")
    WebElement verifyBooking;

    public AdventureDetailsPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    // Methied to verify if we are in adventure detail page.
    public boolean isInAdventureDetailPage(String adventureName) {
        Boolean isInAdventurePage =
                driver.findElement(By.xpath("//h1[text()='" + adventureName + "']")).getText()
                        .equals(adventureName);
        return isInAdventurePage;
    }

    // Method to book an adventure.
    public void bookAdventure(String GuestName, String Date, String count) {

        try {
            Thread.sleep(5000);
            // Enter name of the guest
            SeleniumWrapper.sendKeys(guestNameTextBox, GuestName);
            //guestNameTextBox.sendKeys(GuestName);
            // Enter date of adventure
            SeleniumWrapper.sendKeys(dateBox, Date);
            //dateBox.sendKeys(Date);
            Thread.sleep(3000);
            SeleniumWrapper.sendKeys(personCountBox, count);
            //personCountBox.clear();
            // Enter guest count
            //personCountBox.sendKeys(count);
            Thread.sleep(3000);
            // do the reservation
            SeleniumWrapper.click(reserveButton, driver);
            //reserveButton.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Boolean isBookingSuccessful() {
        // if (reserveButton.isDisplayed()) {
        // return false;
        // }
        // return true;
        if (verifyBooking.getText()
                .contains("Greetings! Reservation for this adventure is successful")) {
            return true;
        } else {
            return false;
        }
    }

}
