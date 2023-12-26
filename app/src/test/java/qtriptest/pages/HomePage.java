package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

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
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20),this);
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

    public void searchCity(String city) throws InterruptedException{
        Thread.sleep(2000);
        //wrapper.sendKeys(search_txtBox, city);
        search_txtBox.clear();
        search_txtBox.sendKeys(city);
        Thread.sleep(1000);
    }

    public Boolean AssertAutoCompleteText(String city){
        if(city_txt.getText().toLowerCase().equals(city.toLowerCase())){
            return true;
        }
        else{
            return false;
        }
    }

    public void selectCity() throws InterruptedException{
        //wrapper.click(city_txt);
        city_txt.click();
        Thread.sleep(5000);
    }

}
