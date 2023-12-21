package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    RemoteWebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register";

    @FindBy(xpath = "//div[contains(text(),'Logout')]")
    private WebElement logout_Btn;
    @FindBy(xpath = "//a[contains(text(),'Register')]")
    private WebElement reg_Btn;
    @FindBy(id = "autocomplete")
    private WebElement search_txtBox;
    @FindBy(id = "results")
    private WebElement city_txt;

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToHome() {
        if (!driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/")) {
            driver.get("https://qtripdynamic-qa-frontend.vercel.app/");
        }
    }

    public void navigateToRegister() {
        if (!driver.getCurrentUrl().endsWith("/register/")) {
            reg_Btn.click();
        }
    }

    public Boolean isUserLoggedIn() {
        return logout_Btn.isDisplayed();
    }

    public Boolean isUserloggedOut() throws InterruptedException {
        // wrapper.click(logout_Btn);
        return reg_Btn.isDisplayed();
    }

    public void logOutUser() {
        logout_Btn.click();
    }

}
