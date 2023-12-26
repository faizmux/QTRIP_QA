package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegisterPage {

    RemoteWebDriver driver;
    public String last_generated_username;

    @FindBy(xpath = "//a[text()='Register']")
    private WebElement registerButton;

    @FindBy(name = "email")
    private WebElement emailTextBox;

    @FindBy(name = "password")
    private WebElement passwordTextBox;

    @FindBy(name = "confirmpassword")
    private WebElement confrimPasswordTextBox;

    @FindBy(xpath = "//button[text()='Register Now']")
    private WebElement registerNowButton;

    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    public RegisterPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }

    public Boolean registerNewUser(String userName, String password, boolean makeUserDynamic)
            throws InterruptedException {
        // SeleniumWrapper wrapper = new SeleniumWrapper(driver);
        if (makeUserDynamic){
        userName = userName+"."+ UUID.randomUUID().toString();
        }
        // wrapper.sendKeys(email_TxtBox, username);
        // wrapper.sendKeys(pass_TxtBox, password);
        // wrapper.sendKeys(conf_pass, password);
        // wrapper.click(reg_Btn);
        emailTextBox.sendKeys(userName);
        passwordTextBox.sendKeys(password);
        confrimPasswordTextBox.sendKeys(password);
        registerNowButton.click();
        this.last_generated_username = userName;
        Thread.sleep(5000);
        return this.driver.getCurrentUrl().endsWith("/login");
    }
}
