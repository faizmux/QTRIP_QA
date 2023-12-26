package qtriptest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage {

    RemoteWebDriver driver;

    @FindBy(xpath = "//a[text()='Login Here']")
    private WebElement loginHereButton;

    @FindBy(name = "email")
    private WebElement emailTextBox;

    @FindBy(name = "password")
    private WebElement passwordTextBox;

    @FindBy(xpath = "//button[text()='Login to QTrip']")
    private WebElement loginButton;

    String loginUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

    public LoginPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToLoginPage(){
        if(driver.getCurrentUrl().equals(loginUrl)){
            driver.get(loginUrl);
        }
    }

    public void performLogin(String username, String password) throws InterruptedException{
        //SeleniumWrapper wrapper = new SeleniumWrapper(driver);
        // wrapper.sendKeys(email_txtBox, username);
        // wrapper.sendKeys(pass_txtBox, password);
        // wrapper.click(login_Btn);
        emailTextBox.sendKeys(username);
        passwordTextBox.sendKeys(password);
        loginButton.click();

        Thread.sleep(2000);
        
        // if(driver.switchTo().alert().getText().equals("You have logged in Succesfully !")){
        //     driver.switchTo().alert().accept();
        // }
        Thread.sleep(5000);
    }
}
